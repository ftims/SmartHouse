<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
 <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable(
        [
          ['Year', 'Temperature'],
          ['2004',  1000],
          ['2005',  1170],
          ['2006',  660],
          ['2007',  1030]
//           x,y
        ]);

        var options = {
          title: 'Company Performance'
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>

<div class="post">
	<div class="entry">

		<div id="chart_div" style="width: 700px; height: 500px;"></div>

	</div>
</div>

