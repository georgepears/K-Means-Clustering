window.onload = function () {
    Chart.defaults.global.defaultFontFamily = 'Ubuntu, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"';
    var ctx = document.getElementById('canvas').getContext('2d');
    window.myScatter = Chart.Scatter(ctx, {
        data: { datasets: clusterSets },
        options: {
            title: {
                display: false,
                text: 'K-Means Data'
            },
        }
    });

    var ctx2 = document.getElementById('canvas2').getContext('2d');
    var chart = new Chart(ctx2, {
        type: 'line',
        data: dunnIndexHistory,
        options: {aspectRatio: 4}
    });

    document.getElementById("dunnIndex").innerHTML = dunnIndex;
    document.getElementById("noAttempts").innerHTML = noAttempts;
    document.getElementById("acceptableDunnIndex").innerHTML = acceptableDunnIndex;
    document.getElementById("dataSetSize").innerHTML = dataSetSize;

    clusterDesc = "";

    for (i = 0; i < clusterSets.length-1; i++) {
        clusterDesc += `
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="card border-primary mb-3" style="border-color: ${clusterSets[i].borderColor} !important">
                    <div class="card-header">${clusterSets[i].label}</div>
                    <div class="card-body">
                        <p class="card-text"><strong>Centroid: </strong>${clusterSets[i].centroid}</p>
                        <p class="card-text"><strong>No. Points: </strong>${clusterSets[i].size}</p>
                        <p class="card-text"><strong>% of Dataset: </strong>${clusterSets[i].percentage}%</p>
                    </div>
                </div>
            </div>`
    }

    document.getElementById("clusters").innerHTML = clusterDesc;
    
    if (noAttempts <= 1) {
        document.getElementById("dunnGraph").innerHTML = "<p>Only one attempt was made. If multiple attempts are made, a graph will show here displaying the Dunn Index measures from previous, less successful attempts.</p><p class='text-center pt-5 pb-5'><b>Only 1 attempt made &#128522</b></p>";
    }

    

};