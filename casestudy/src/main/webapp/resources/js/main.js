$(document).ready(function (){
	$('.selectBtn').on('change', function () {
		$("#responseMsg").html('');
		$('#uploadFile').val($(this).val());
	});
	
	
	$('.navclick').on('click', function() {
		$("#responseMsg").html('');
		$('.navclick').removeClass('selected');
		var thisEle = $(this);
		thisEle.addClass('selected');
		if (thisEle.attr('data-attr') === 'uploadFile'){
			$('.uploadFileContainer').show();
			$('.exportFileContainer').hide();
		}
		else if (thisEle.attr('data-attr') === 'exportFile'){
			$('.exportFileContainer').show();
			$('.uploadFileContainer').hide();
			
			$.ajax({
				type: 'GET',
				url: 'getfile/list.json',
				contentType: 'application/json',
				cache: false,
				success: function(resp) {
					var optionList = "";
					if(resp !== null && resp !== ''){
						var list = JSON.parse(resp);
						try {
						$.each(list, function(k, val) {
							optionList +='<option value="'+val+'">'+val+'</option>';
						});
						$('.fileOptionList').html(optionList);
						} catch (e) {
							console.log("ERROR : ", e.responseText);
						}
					} else {
						$('.fileOptionList').html('<option value="">No File</option>');
					}
				},
				error: function(e) {
					console.log("ERROR : ", e.responseText);
				}				
			});
		}
		
	});
	
	
	// upload file
	$('.uploadFileBtn').on('click', function(e) {
		e.preventDefault();
		var fileName = $('#uploadFile').val();
		if (fileName === ''){
			$("#responseMsg").html('<span class="error">Please select a file!</span>');
			return false;
		} else if (fileName.slice(-4) !== '.csv'){
			$("#responseMsg").html('<span class="error">Please select valid csv file only!</span>');
			return false;
		}
		var form = $('#fileUploadForm')[0];
		// Create an FormData object
        var data = new FormData(form);
        data.append("CustomField", "This is some extra data, testing");
        $(".uploadFileBtn").prop("disabled", true);
        
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "upload/file.html",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            beforeSend: function() {
				$('.loader').show();
			},
            success: function (data) {

                $("#responseMsg").html('<span class="success">'+data+'</span>');
                console.log("SUCCESS : ", data);
                $(".uploadFileBtn").prop("disabled", false);
                form.reset();
                $('.loader').hide();
            },
            error: function (e) {

                $("#responseMsg").html('<span class="success">'+e.responseText+'</span>');
                console.log("ERROR : ", e);
                $(".uploadFileBtn").prop("disabled", false);
                $('.loader').hide();

            }
        });
	});
	
	$('.convertFileBtn').on('click', function(e) {
		e.preventDefault();
		var jsonObj={};
		
			jsonObj.fileName = $('.fileOptionList').val();
			jsonObj.fileType = $('.fileType').val();
		
		var jsonObj = JSON.stringify(jsonObj);
		
		$.ajax({
			type: 'POST',
			url: 'convert/file.json',
			data: 'jsonObj='+jsonObj,
			cache: false,
			beforeSend: function() {
				$('.loader').show();
			},
			success: function(resp) {
				if (resp !== ''){
					$("#responseMsg").html('<span class="success">File converted at@'+resp+'</span>');
				} else {
					$("#responseMsg").html('<span class="error">Selected file not exist.</span>');
				}
				$('.loader').hide();
				console.log(resp);
			},
			error: function(e) {
				$('.loader').hide();
				console.log("ERROR : ", e.responseText);
			}				
		});
		
		console.log(jsonObj);
	});
	
});