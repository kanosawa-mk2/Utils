package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TempDir {

	public static void main(String[] args) {
		try {
			// https://www.web-dev-qa-db-ja.com/ja/tomcat/tomcat-7%E3%81%AEtomcat-temp%E3%83%87%E3%82%A3%E3%83%AC%E3%82%AF%E3%83%88%E3%83%AA%E3%81%A8%E3%81%AF%E4%BD%95%E3%81%A7%E3%81%99%E3%81%8B%EF%BC%9F/940305695/
			
			String tmpDirectory = System.getProperty("java.io.tmpdir");
			
			System.out.println("java.io.tmpdir = " + tmpDirectory);
			// createTempDirectoryはスレッドセーフ 名前がかぶってもリトライを内部的にしている
			// https://stackoverflow.com/questions/4702537/is-createtempfile-thread-safe
			Path tempPath = Files.createTempDirectory(Paths.get(tmpDirectory), "prefix_");
			
			System.out.println("tempPath ="  + tempPath);
			
			Path tempPath2 = Files.createTempDirectory("prefix_");
			
			System.out.println("tempPath ="  + tempPath2);
			
			
//			string filename = "File01";
//			try{
//			　　//サーバー上にファイル出力
//			　　srvOutput(filename);
//
//			　　//ダウンロード処理
//			　　Response.AppendHeader("Content-Disposition","attachment;filename="+filename );
//			　　Response.ContentType="Application/octet-stream";
//			　　Response.WriteFile(filename);
//			　　Response.Flush();
//			}
//			catch( Exception ex ){
//			　　//エラー処理？
//			}
//			finally{
//			　　System.IO.File.Delete(filename);
//			}
			// https://codezine.jp/article/detail/123
			// https://qiita.com/SakuraiYuki/items/108a3b8e884facf4ea80
			// Files.deleteIfExists(temporaryFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
