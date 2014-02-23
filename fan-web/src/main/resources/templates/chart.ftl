<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#import "common.ftl" as c />


<#assign pageTitle><@spring.message "index.title" /></#assign>

<@layout.layout pageTitle="${pageTitle}">

	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Year', 'Sales', 'Expenses'],
          ['2004',  1000,      400],
          ['2005',  1170,      460],
          ['2006',  660,       1120],
          ['2007',  1030,      540]
        ]);

        var options = {
          title: 'Company Performance',
          hAxis: {title: 'Year',  titleTextStyle: {color: 'red'}}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>

	<h1><@c.i18ned content "title" /></h1>
    <p><@c.i18ned content "text" /></p>
</@layout.layout>
