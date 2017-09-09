<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script>
<script src="<c:url value="/resources/js/main.js" />"></script>
<title>Home</title>
</head>
<body>
	<div class="mainContainer">
		<div class="container-wrapper">
			<header>
				<h1>Case Study</h1>
			</header>
			<nav class="navigation">
				<ul>
					<li data-attr="uploadFile" class="navclick selected">Upload File</li>
					<li data-attr="exportFile" class="navclick">Download File</li>
				</ul>
			</nav>
			<div class="loader"><img src="resources/images/loader.gif" alt="loader" /></div>
			<section class="uploadFileContainer">
			<form id="fileUploadForm" method="post" enctype="multipart/form-data">
				<div class="uploadlabel">Upload File<em>*</em></div>

				<div class="fileinput">
					<input id="uploadFile" type="text" disabled="disabled"
						placeholder="Choose File">
				</div>

				<div class="file-upload btn btn-primary">
					<span>Select</span> <input id="fileToUpload" class="upload selectBtn"
						type="file" name="file">
				</div>
				<div class="uploadbtn">
					<button class="btn btn-primary uploadFileBtn" type="submit" value="Submit" >Upload</button>
				</div>
			</form>
			</section>
			
			<section class="exportFileContainer">
				<form id="exportFile" method="post">
				<div class="inner-wrapper">
					<div class="file-dropdown-container">
						<label>Select File:</label>
						<select name="file-list" class="fileOptionList">
							<!-- <option>test</option>  -->
						</select>
					</div>
					<div class="extension-dropdown-container">
						<label>Select File Type:</label>
						<select name="fileType" class="fileType">
							<option value="json">JSON</option>
							<option value="xml">XML</option>
						</select>
					</div>
					<div class="convertBtn">
						<button class="btn btn-primary convertFileBtn" type="submit" value="download" >Convert</button>
					</div>
				</div>
				</form>
			</section>
			<div class="msgDisplay" id="responseMsg"></div>
		</div>

	</div>



</body>
</html>
