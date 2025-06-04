package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OptimisticLockingWithRetry {
	private static final int MAX_RETRIES = 3;

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:snowflake://<account>.snowflakecomputing.com/?db=TEST_DB&schema=PUBLIC";
		String user = "your_user";
		String password = "your_password";

		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			conn.setAutoCommit(false);

			boolean success = false;
			int retryCount = 0;

			while (!success && retryCount < MAX_RETRIES) {
				retryCount++;

				try {
					// 最新データを読み込む
					String accountId = "A123";
					int currentVersion = 0;
					int currentBalance = 0;

					try (PreparedStatement selectStmt = conn.prepareStatement(
							"SELECT balance, version FROM accounts WHERE account_id = ?")) {
						selectStmt.setString(1, accountId);
						ResultSet rs = selectStmt.executeQuery();
						if (rs.next()) {
							currentBalance = rs.getInt("balance");
							currentVersion = rs.getInt("version");
						} else {
							throw new SQLException("Account not found.");
						}
					}

					// 新しい値を計算
					int newBalance = currentBalance - 100;

					// 楽観的ロック付きの更新
					try (PreparedStatement updateStmt = conn.prepareStatement(
							"UPDATE accounts SET balance = ?, version = version + 1 WHERE account_id = ? AND version = ?")) {
						updateStmt.setInt(1, newBalance);
						updateStmt.setString(2, accountId);
						updateStmt.setInt(3, currentVersion);

						int updatedRows = updateStmt.executeUpdate();
						if (updatedRows == 1) {
							conn.commit();
							System.out.println("Update successful on retry #" + retryCount);
							success = true;
						} else {
							conn.rollback();
							System.out.println("Version mismatch. Retrying... (" + retryCount + ")");
						}
					}
				} catch (SQLException e) {
					conn.rollback();
					System.err.println("Error during update: " + e.getMessage());
					throw e;
				}
			}

			if (!success) {
				System.out.println("Update failed after maximum retries.");
			}
		}
	}
}
