<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});
	google.setOnLoadCallback(drawChart);
	function drawChart() {
		var component = jQuery('#component').val();
		var type = jQuery('#type').val();
		var jsonData = $.ajax({
			url : "getWeatherData.html?component=" + component + "&type="
					+ type + "",
			dataType : "json",
			async : false
		}).responseText;
		var options = {
			title : 'Weather Data Chart 2010: ' + component + ' per a ' + type + '',
			hAxis : {
				title : 'time of messure'
			},
			vAxis : {
				title : 'value of ' + component
			}

		};
		var data = new google.visualization.DataTable(jsonData);
		var chart = new google.visualization.LineChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
</script>
<div class="post">
	<div class="entry">
		<input type="hidden" id="component" name="component"
			value="${component}" /> <input type="hidden" id="type" name="type"
			value="${type}" />
		<div id="chart_div" style="width: 800px; height: 500px;"></div>

	</div>
</div>

