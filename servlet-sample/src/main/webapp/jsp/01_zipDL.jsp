<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ZIPダウンロードサンプル</title>
<script>
(() => {
	var ctx = "<%=request.getContextPath()%>";

	document.addEventListener('DOMContentLoaded', function() {
		let button = document.getElementById('zipDl');
		button.addEventListener('click', function() {
			let path = document.getElementById('path').value;
			let zipName = document.getElementById('zipName').value;

			let url = ctx + "/ZipDownload?path=" + path + "&zipNm=" + zipName;
			url = encodeURI(url);
			window.open(url,'_blank');
		});
	});

})();
</script>
</head>
<body>
	ZIP名
	<input type="text" id="zipName" name="zipName" />
	<br> ファイルパス
	<input type="text" id="path" name="path" />
	<br>
	<input type="button" value="ZIPダウンロード" id="zipDl" />
</body>
</html>