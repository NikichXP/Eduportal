﻿$(function(){
			
	//open order menu
	$('#menu-order-open').on("click", function() {
				
		$('#table-order-menu').html("<tbody></tbody>");
		$('#order-menu-text-block').html("<H2>Мои клиенты</H2>");
		$('#order-new').css('display', 'none');
		
		$.ajax({
		type: 'GET',
		url: 'https://beta-dot-eduportal-1277.appspot.com/_ah/api/user/v1/getMyorders',
		data: tokenJson,
		success: function(resData) {
			var imax = resData.items.length;
			var count = 0;
			for (var i = 0; i < resData.items.length; i++)
			{
				if (count == imax) break;
				$('#table-order-menu tbody').append("<tr id='tr-order-menu-" + (i + 1) + "'>");
				for (var j = 0; j < 5; j++)
				{
					$('#tr-order-menu-' + (i + 1)).append(
					"<td class='td-order-menu'>" + resData.items[count].name + " " + resData.items[count].surname + 
					"<input type='hidden' class='inner-order-menu-input-1' value=" + resData.items[count].id + ">" +
					"</td>"
					);
					count++;
					if (count == imax) break;
				}
				$('#table-order-menu').append("</tr>");				
			}
			$('#order-menu').css('display', 'block');
			$('#opacity').css('display', 'block');
		},
		});	
	});
	
	//order creation menu
	$('#order-menu-create').on('click', function() {
		
		$('#order-menu-create').html("Принять");
		
		if ($('#order-new').css('display') == 'block')
		{
			$('#order-new').css('display', 'none');
			$('#order-menu-create').html("Добавить клиента");
			$('#order-menu').css('height', '322px');
			
			var clData = new FormData();    
			clData.append('token', authSesToken);
			clData.append('mail', $('#order-new-email').val());
			clData.append('name', $('#order-new-name').val());
			clData.append('surname', $('#order-new-surname').val());
			clData.append('phone', $('#order-new-phone').val());
			clData.append('pass', '');
			
			$.ajax({
			type: 'POST',
			url: 'https://beta-dot-eduportal-1277.appspot.com/_ah/api/user/v1/createuser',
			data: clData,
			processData: false,
			contentType: false,
			success: location.reload(),	
			});	
		}
		else 
		{
			$('#order-new').css('display', 'block');
			$('#order-menu').css('height', '637px');
			$('#order-menu-dismiss').css('display', 'block');
		}
		
	});
	//cancel order creation
	$('#order-menu-dismiss').on('click', function() {
		window.close();
	});

	$('#select-order-day').html(" ");
	for (var i = 1; i < 32; i++)
	{
		$('#select-order-day').append("<option id='day-" + i + "'>" + i + "</option>");
	}
	$('#select-order-month').html(" ");
	for (var i = 1; i < 13; i++)
	{
		$('#select-order-month').append("<option id='month-" + i + "'>" + i + "</option>");
	}
	$('#select-order-year').html(" ");
	for (var i = 1; i < 21; i++)
	{
		$('#select-order-year').append("<option id='year-" + (i + 1980) + "'>" + (i + 1980) + "</option>");
	}

	$('#select-order-month').on('change', function() {
  		if ($('#select-order-month').find(":selected").text() == '2')
  		{
  			$('#select-order-day').html(" ");

			for (var i = 1; i < 30; i++)
			{
				$('#select-order-day').append("<option id='day-" + i + "'>" + i + "</option>");
			}
		}
	});

	$('#select-pas-day').html(" ");
	for (var i = 1; i < 32; i++)
	{
		$('#select-pas-day').append("<option id='day-" + i + "'>" + i + "</option>");
	}
	$('#select-pas-month').html(" ");
	for (var i = 1; i < 13; i++)
	{
		$('#select-pas-month').append("<option id='month-" + i + "'>" + i + "</option>");
	}
	$('#select-pas-year').html(" ");
	for (var i = 1; i < 21; i++)
	{
		$('#select-pas-year').append("<option id='year-" + (i + 2015) + "'>" + (i + 2015) + "</option>");
	}

	$('#select-pas-month').on('change', function() {
  		if ($('#select-pas-month').find(":selected").text() == '2')
  		{
  			$('#select-pas-day').html(" ");

			for (var i = 1; i < 30; i++)
			{
				$('#select-pas-day').append("<option id='day-" + i + "'>" + i + "</option>");
			}
		}
	});

	
	$.ajax({
		type: 'GET',
		url: 'https://beta-dot-eduportal-1277.appspot.com/_ah/api/user/v1/getname',
		data: {'token' : getCookie("sesToken")},
		success: function(resData) {
			$('#order-new-emp').val(resData.name + " " + resData.surname);
		},	
	});

	$.ajax({
		type: 'GET',
		url: 'https://beta-dot-eduportal-1277.appspot.com/_ah/api/order/v1/products',
		data: {'token' : getCookie("sesToken")},
		success: function(resData) {
			for (var i = 0; i < resData.items.length; i++)
			{
				$('#select-product').append("<option class='" + resData.items[i].currency + "' value='" + resData.items[i].id + "'>" 
					+ resData.items[i].title
					+ "</option>" 
				);
			}
		},
	});	

	$('#select-product').on('change', function() {
			$('#order-currency').val($('#select-product').find(":selected").attr("class"));
	});

	$.ajax({
		type: 'GET',
		url: 'https://beta-dot-eduportal-1277.appspot.com/_ah/api/user/v1/getMyClients',
		data: {'token' : getCookie("sesToken")},
		success: function(resData) {
			if (resData)
				for (var i = 0; i < resData.items.length; i++)
				{
					$('#select-client').append("<option id='client-" + i + "' value='" + resData.items[i].id + "'>" 
					+ resData.items[i].name + " " + resData.items[i].surname 
					+ "</option>" 
					);	
				}
		},
	});	

	//send new order
	$('#order-menu-send').on('click', function() {
				{
			var orderData = {
				token: getCookie("sesToken"),
				productid: $('#select-product').find(":selected").val(),
				clientid: $('#select-client').find(":selected").val(),
				paid: $('#order-new-sum').val(),
				year: $('#select-year').find(":selected").val(),
				comment: $('#order-comment').val(),
			};

			$.ajax({
				type: 'GET',
				url: 'https://beta-dot-eduportal-1277.appspot.com/_ah/api/order/v1/createorder',
				data: orderData,
				success: window.close(),
			});	
			
		};	
			
	});	
});


