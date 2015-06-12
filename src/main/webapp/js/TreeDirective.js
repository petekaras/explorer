app.directive('treeVisualization', function() {
	// define constants and helpers used for the directive
	// ...
	var width = 600, height = 1000;

	var svg = d3.select("body").append("svg").attr("width", width).attr("height", height).append("g").attr("transform", "translate(40,0)");
	return {
		restrict : 'E', // the directive can be invoked only by using
		// <my-directive> tag in the template
		scope : { // attributes bound to the scope of the directive
			val : '='
		},
		link : function(scope, element, attrs) {

			// initialization, done once per my-directive tag in template. If
			// my-directive is within an
			// ng-repeat-ed template then it will be called every time ngRepeat
			// creates a new copy of the template.

			// whenever the bound 'exp' expression changes, execute this
			scope.$watch('val', function(newVal, oldVal) {
				// Remove old graph
				d3.select("svg").remove();
				// Draw new graph
				var cluster = d3.layout.cluster().size([ height, width - 160 ]);

				var diagonal = d3.svg.diagonal().projection(function(d) {
					return [ d.y, d.x ];
				});

				var svg = d3.select("tree-visualization").append("svg").attr("width", width).attr("height", height).append("g").attr("transform", "translate(40,0)");

				// TODO: Only if newVal is defined
				if (typeof newVal != 'undefined') {
					var root = newVal;
					var nodes = cluster.nodes(root), links = cluster.links(nodes);

					var link = svg.selectAll(".link").data(links).enter().append("path").attr("class", "link").attr("d", diagonal);

					var node = svg.selectAll(".node").data(nodes).enter().append("g").attr("class", "node").attr("transform", function(d) {
						return "translate(" + d.y + "," + d.x + ")";
					})

					node.append("circle").attr("r", 4.5);

					node.append("text").attr("dx", function(d) {
						return d.children ? -8 : 8;
					}).attr("dy", 3).style("text-anchor", function(d) {
						return d.children ? "end" : "start";
					}).text(function(d) {
						return d.name;
					});

					d3.select(self.frameElement).style("height", height + "px");
				}
			});
		}
	};
});