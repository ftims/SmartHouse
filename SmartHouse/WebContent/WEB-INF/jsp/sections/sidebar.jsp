
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div id="sidebar">
	<ul>

		<li>
			<h2>
				Show last week <br> <small><em>(demo 01.01.2010-01.08.2010)</em></small>
			</h2>
			<ul>
				<li><a href="data.html?component=temperature&type=week">Temperature</a></li>
				<li><a href="data.html?component=pressure&type=week">Pressure</a></li>
				<li><a href="data.html?component=insolation&type=week">Insolation</a></li>
				<li><a href="data.html?component=humidity&type=week">Humidity</a></li>
				<li><a href="data.html?component=wind_speed&type=week">Wind
						speed</a></li>
				<li><a href="data.html?component=wind_direction&type=week">Wind
						direction</a></li>
				<!-- 				<li><a href="static.html">Static demo</a></li> -->
			</ul> <br>
			<h2>
				Show last day <br>
				<small><em>(demo 01.01.2010)</em></small>
			</h2>
			<ul>
				<li><a href="data.html?component=temperature&type=day">Temperature</a></li>
				<li><a href="data.html?component=pressure&type=day">Pressure</a></li>
				<li><a href="data.html?component=insolation&type=day">Insolation</a></li>
				<li><a href="data.html?component=humidity&type=day">Humidity</a></li>
				<li><a href="data.html?component=wind_speed&type=day">Wind
						speed</a></li>
				<li><a href="data.html?component=wind_direction&type=day">Wind
						direction</a></li>

			</ul>
		</li>

	</ul>
</div>
<!-- end #sidebar -->

<div style="clear: both;">&nbsp;</div>