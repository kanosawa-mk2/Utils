# サンプルWEBプロジェクト

## 簡単なサーブレット実験用

### このプロジェクトの作成方法
eclipseからMavenプロジェクトを作成
そのときパッケージングを「war」にするとWebプロジェクト扱いにしてくれるらしい(かしこい！)
後はweb.xmlやサーブレットクラスなどを追加するだけ

### jettyでAPサーバをインストール不要で動かす

[公式サイト](https://eclipse.dev/jetty/)

- 前提条件  
mavenやgradle


- 手順(maven編)

① pom.xmlに以下を追加する

	<properties>
		<jetty.version>9.4.53.v20231009</jetty.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.eclipse.jetty</groupId>
			<artifactId>jetty-server</artifactId>
			<version>${jetty.version}</version>
		</dependency>
		<dependency>
			<groupId>org.eclipse.jetty</groupId>
			<artifactId>jetty-webapp</artifactId>
			<version>${jetty.version}</version>
		</dependency>
	</dependencies>
	<build>
		<finalName>test-server</finalName>
		<plugins>
			<plugin>
				<groupId>org.eclipse.jetty</groupId>
				<artifactId>jetty-maven-plugin</artifactId>
				<version>${jetty.version}</version>
				<configuration>
					<httpConnector>
						<port>8989</port>
					</httpConnector>
				</configuration>
			</plugin>
		</plugins>
	</build>

② mavenコマンドで以下を実行する

	mvn jetty:run

eclipseから起動する場合

pom.xmlを右クリック ＞ 実行 ＞ Mavenビルド...  
ゴールに「jetty:run」を入力し実行