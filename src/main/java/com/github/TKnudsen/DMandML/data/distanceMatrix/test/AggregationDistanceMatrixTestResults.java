package com.github.TKnudsen.DMandML.data.distanceMatrix.test;

/**
 * Test for the AggregationDistanceMatrix data structure.
 * 
 * ELEMENTS: 2000 DIM: 5:
 * 
 * K: 3, per cluster: 666, error: 29.66%, memory: 33.45%, duration: 134.76%
 * 
 * K: 5, per cluster: 400, error: 29.3%, memory: 20.02%, duration: 131.42%
 * 
 * K: 7, per cluster: 285, error: 27.25%, memory: 14.41%, duration: 101.88%
 * 
 * K: 10, per cluster: 200, error: 24.95%, memory: 10.11%, duration: 86.53%
 * 
 * K: 13, per cluster: 153, error: 23.65%, memory: 7.84%, duration: 103.4%
 * 
 * K: 16, per cluster: 125, error: 22.12%, memory: 6.32%, duration: 111.08%
 * 
 * K: 20, per cluster: 100, error: 21.16%, memory: 5.12%, duration: 139.43%
 * 
 * K: 25, per cluster: 80, error: 20.04%, memory: 4.12%, duration: 213.83%
 * 
 * K: 30, per cluster: 66, error: 19.42%, memory: 3.41%, duration: 218.65%
 * 
 * K: 35, per cluster: 57, error: 18.83%, memory: 2.97%, duration: 193.55%
 * 
 * K: 40, per cluster: 50, error: 18.13%, memory: 2.64%, duration: 486.49%
 * 
 * K: 45, per cluster: 44, error: 17.73%, memory: 2.4%, duration: 285.45%
 * 
 * K: 50, per cluster: 40, error: 17.31%, memory: 2.13%, duration: 256.11%
 * 
 * K: 60, per cluster: 33, error: 16.34%, memory: 1.85%, duration: 263.96%
 * 
 * K: 70, per cluster: 28, error: 15.78%, memory: 1.61%, duration: 248.59%
 * 
 * K: 80, per cluster: 25, error: 15.23%, memory: 1.47%, duration: 248.35%
 * 
 * K: 90, per cluster: 22, error: 14.71%, memory: 1.38%, duration: 517.64%
 * 
 * K: 100, per cluster: 20, error: 14.29%, memory: 1.31%, duration: 557.87%
 * 
 * K: 125, per cluster: 16, error: 13.45%, memory: 1.25%, duration: 382.18%
 * OPTIMUM SOMEWHERE HERE
 * 
 * K: 150, per cluster: 13, error: 12.81%, memory: 1.29%, duration: 470.91%
 * 
 * K: 200, per cluster: 10, error: 11.72%, memory: 1.56%, duration: 532.5%
 * 
 * K: 333, per cluster: 6, error: 9.99%, memory: 3.13%, duration: 649.37%
 * 
 * K: 500, per cluster: 4, error: 8.41%, memory: 6.5%, duration: 832.69%
 * 
 * K: 1000, per cluster: 2, error: 5.46%, memory: 25.13%, duration: 1226.16%
 * 
 * 
 * ELEMENTS: 4000 DIM: 5:
 * 
 * K: 10, per cluster: 400, error: 25.01%, memory: 10.05%, duration: 100.83%
 * 
 * K: 15, per cluster: 266, error: 22.86%, memory: 6.74%, duration: 189.71%
 * 
 * K: 20, per cluster: 200, error: 21.19%, memory: 5.06%, duration: 193.98%
 * 
 * K: 30, per cluster: 133, error: 19.34%, memory: 3.39%, duration: 213.04%
 * 
 * K: 40, per cluster: 100, error: 18.2%, memory: 2.57%, duration: 276.65%
 * 
 * K: 50, per cluster: 80, error: 17.23%, memory: 2.07%, duration: 306.69%
 * 
 * K: 60, per cluster: 66, error: 16.7%, memory: 1.73%, duration: 332.87%
 * 
 * K: 70, per cluster: 57, error: 15.96%, memory: 1.5%, duration: 467.57%
 * 
 * K: 80, per cluster: 50, error: 15.61%, memory: 1.33%, duration: 456.53%
 * 
 * K: 90, per cluster: 44, error: 15.16%, memory: 1.2%, duration: 373.18%
 * 
 * K: 100, per cluster: 40, error: 14.71%, memory: 1.1%, duration: 370.7%
 * 
 * K: 150, per cluster: 26, error: 13.29%, memory: 0.84%, duration: 651.4%
 * OPTIMUM SOMEWHERE HERE
 * 
 * K: 200, per cluster: 20, error: 12.26%, memory: 0.78%, duration: 695.66%
 * 
 * K: 333, per cluster: 12, error: 10.57%, memory: 1.02%, duration: 709.02%
 * 
 * K: 500, per cluster: 8, error: 9.29%, memory: 1.79%, duration: 1077.13%
 * 
 * K: 1000, per cluster: 4, error: 7.12%, memory: 6.37%, duration: 1661.72%
 * 
 * 
 * ELEMENTS: 10000 DIM: 5:
 * 
 * K: 10, per cluster: 1000, error: 25.15%, memory: 10.01%, duration: 27.79%
 * 
 * K: 15, per cluster: 666, error: 22.65%, memory: 6.7%, duration: 29.44%
 * 
 * K: 20, per cluster: 500, error: 21.29%, memory: 5.09%, duration: 25.37%
 * 
 * K: 30, per cluster: 333, error: 19.49%, memory: 3.38%, duration: 60.15%
 * 
 * K: 40, per cluster: 250, error: 18.42%, memory: 2.54%, duration: 61.65%
 * 
 * K: 50, per cluster: 200, error: 17.54%, memory: 2.03%, duration: 134.48%
 * 
 * K: 60, per cluster: 166, error: 16.88%, memory: 1.7%, duration: 190.7%
 * 
 * K: 70, per cluster: 142, error: 16.26%, memory: 1.46%, duration: 147.83%
 * 
 * K: 80, per cluster: 125, error: 15.75%, memory: 1.27%, duration: 129.69%
 * 
 * K: 90, per cluster: 111, error: 15.31%, memory: 1.14%, duration: 262.87%
 * 
 * K: 100, per cluster: 100, error: 14.93%, memory: 1.03%, duration: 117.13%
 * 
 * K: 150, per cluster: 66, error: 13.56%, memory: 0.7%, duration: 180.65%
 * 
 * K: 200, per cluster: 50, error: 12.68%, memory: 0.55%, duration: 250.65%
 * 
 * K: 333, per cluster: 30, error: 11.2%, memory: 0.42%, duration: 243.85%
 * OPTIMUM SOMEWHERE HERE
 * 
 * K: 500, per cluster: 20, error: 10.04%, memory: 0.46%, duration: 455.13%
 * 
 * K: 1000, per cluster: 10, error: 8.23%, memory: 1.11%, duration: 318.17%
 * 
 * 
 * ELEMENTS: 1000 DIM: 15:
 * 
 * K: 10, per cluster: 100, error: 45.61%, memory: 10.1%, duration: 222.66%
 * 
 * K: 15, per cluster: 66, error: 43.37%, memory: 6.81%, duration: 457.04%
 * 
 * K: 20, per cluster: 50, error: 41.26%, memory: 5.22%, duration: 239.57%
 * 
 * K: 30, per cluster: 33, error: 37.7%, memory: 3.57%, duration: 260.46%
 * 
 * K: 40, per cluster: 25, error: 35.3%, memory: 2.73%, duration: 341.36%
 * 
 * K: 50, per cluster: 20, error: 32.98%, memory: 2.37%, duration: 557.82%
 * 
 * K: 60, per cluster: 16, error: 31.5%, memory: 2.14%, duration: 317.88%
 * 
 * K: 70, per cluster: 14, error: 30.43%, memory: 2.06%, duration: 594.15%
 * 
 * K: 80, per cluster: 12, error: 28.96%, memory: 2.02%, duration: 961.63%
 * 
 * K: 90, per cluster: 11, error: 28.19%, memory: 2.06%, duration: 781.08%
 * 
 * K: 100, per cluster: 10, error: 27.15%, memory: 2.15%, duration: 526.93%
 * 
 * K: 150, per cluster: 6, error: 23.64%, memory: 3.07%, duration: 652.02%
 * 
 * K: 200, per cluster: 5, error: 20.94%, memory: 4.64%, duration: 703.73%
 * 
 * K: 333, per cluster: 3, error: 15.35%, memory: 11.49%, duration: 980.83%
 * 
 * K: 500, per cluster: 2, error: 10.56%, memory: 25.26%, duration: 1187.24%
 * 
 * K: 1000, per cluster: 1, error: 0.0%, memory: 100.1%, duration: 1533.15%
 * 
 * * ELEMENTS: 5000 DIM: 15:
 * 
 * K: 10, per cluster: 500, error: 48.4%, memory: 10.02%, duration: 59.64%
 * 
 * K: 15, per cluster: 333, error: 46.18%, memory: 6.68%, duration: 111.53%
 * 
 * K: 20, per cluster: 250, error: 44.38%, memory: 5.04%, duration: 115.87%
 * 
 * K: 30, per cluster: 166, error: 41.37%, memory: 3.36%, duration: 103.48%
 * 
 * K: 40, per cluster: 125, error: 39.38%, memory: 2.54%, duration: 95.28%
 * 
 * K: 50, per cluster: 100, error: 37.75%, memory: 2.04%, duration: 213.04%
 * 
 * K: 60, per cluster: 83, error: 36.17%, memory: 1.69%, duration: 204.3%
 * 
 * K: 70, per cluster: 71, error: 35.1%, memory: 1.47%, duration: 166.42%
 * 
 * K: 80, per cluster: 62, error: 34.01%, memory: 1.3%, duration: 152.06%
 * 
 * K: 90, per cluster: 55, error: 33.19%, memory: 1.17%, duration: 201.47%
 * 
 * K: 100, per cluster: 50, error: 32.49%, memory: 1.07%, duration: 171.8%
 * 
 * K: 150, per cluster: 33, error: 29.3%, memory: 0.78%, duration: 360.07%
 * 
 * K: 200, per cluster: 25, error: 27.26%, memory: 0.68%, duration: 233.87%
 * 
 * K: 333, per cluster: 15, error: 23.73%, memory: 0.77%, duration: 272.4%
 * 
 * K: 500, per cluster: 10, error: 20.96%, memory: 1.22%, duration: 270.48%
 * 
 * K: 1000, per cluster: 5, error: 16.18%, memory: 4.12%, duration: 392.25%
 * 
 * 
 * * ELEMENTS: 2000, DIM: 25
 * 
 * K: 1333, per cluster: 1.5, error: 8.36%, memory: 44.52%, duration: 908.02%
 * 
 * K: 1000, per cluster: 2.0, error: 13.5%, memory: 25.14%, duration: 1382.56%
 * 
 * K: 666, per cluster: 3.0, error: 19.96%, memory: 11.31%, duration: 762.82%
 * 
 * K: 500, per cluster: 4.0, error: 24.02%, memory: 6.53%, duration: 886.45%
 * 
 * K: 400, per cluster: 5.0, error: 26.86%, memory: 4.34%, duration: 685.14%
 * 
 * K: 200, per cluster: 10.0, error: 34.56%, memory: 1.61%, duration: 582.02%
 * 
 * ELEMENTS: 1000 DIM: 50:
 * 
 * K: 10, per cluster: 100, error: 62.84%, memory: 10.26%, duration: 199.63%
 * 
 * K: 15, per cluster: 66, error: 61.98%, memory: 6.9%, duration: 171.93%
 * 
 * K: 20, per cluster: 50, error: 60.69%, memory: 5.35%, duration: 255.93%
 * 
 * K: 30, per cluster: 33, error: 58.32%, memory: 3.68%, duration: 242.28%
 * 
 * K: 40, per cluster: 25, error: 56.56%, memory: 3.03%, duration: 247.79%
 * 
 * K: 50, per cluster: 20, error: 54.86%, memory: 2.65%, duration: 309.99%
 * 
 * K: 60, per cluster: 16, error: 53.25%, memory: 2.45%, duration: 472.88%
 * 
 * K: 70, per cluster: 14, error: 52.0%, memory: 2.35%, duration: 392.65%
 * 
 * K: 80, per cluster: 12, error: 50.67%, memory: 2.29%, duration: 392.37%
 * 
 * K: 90, per cluster: 11, error: 49.46%, memory: 2.33%, duration: 395.61%
 * 
 * K: 100, per cluster: 10, error: 48.75%, memory: 2.41%, duration: 406.98%
 * 
 * K: 150, per cluster: 6, error: 43.15%, memory: 3.23%, duration: 711.37%
 * 
 * K: 200, per cluster: 5, error: 38.7%, memory: 4.75%, duration: 629.74%
 * 
 * K: 333, per cluster: 3, error: 28.71%, memory: 11.56%, duration: 867.93%
 * 
 * K: 500, per cluster: 2, error: 19.13%, memory: 25.3%, duration: 971.6%
 * 
 * K: 1000, per cluster: 1, error: 0.0%, memory: 100.1%, duration: 1392.77%
 * 
 * 
 * * ELEMENTS: 2000, DIM: 50
 * 
 * K: 2000, per cluster: 1.0, error: 0.0%, memory: 100.05%, duration: 1134.55%
 * 
 * K: 1000, per cluster: 2.0, error: 18.58%, memory: 25.16%, duration: 944.67%
 * 
 * K: 666, per cluster: 3.0, error: 27.92%, memory: 11.36%, duration: 590.65%
 * 
 * K: 500, per cluster: 4.0, error: 33.71%, memory: 6.61%, duration: 332.44%
 * 
 * K: 400, per cluster: 5.0, error: 37.51%, memory: 4.46%, duration: 402.31%
 * 
 * K: 200, per cluster: 10.0, error: 46.77%, memory: 1.79%, duration: 188.43%
 * 
 * ELEMENTS: 2000 DIM: 100:
 * 
 * K: 10, per cluster: 200, error: 70.46%, memory: 10.1%, duration: 119.2%
 * 
 * K: 15, per cluster: 133, error: 71.03%, memory: 6.79%, duration: 92.46%
 * 
 * K: 20, per cluster: 100, error: 70.41%, memory: 5.21%, duration: 99.71%
 * 
 * K: 30, per cluster: 66, error: 68.95%, memory: 3.58%, duration: 112.93%
 * 
 * K: 40, per cluster: 50, error: 67.89%, memory: 2.87%, duration: 88.33%
 * 
 * K: 50, per cluster: 40, error: 66.47%, memory: 2.42%, duration: 144.31%
 * 
 * K: 60, per cluster: 33, error: 65.71%, memory: 2.15%, duration: 158.19%
 * 
 * K: 70, per cluster: 28, error: 64.63%, memory: 2.01%, duration: 148.12%
 * 
 * K: 80, per cluster: 25, error: 63.86%, memory: 1.92%, duration: 145.79%
 * 
 * K: 90, per cluster: 22, error: 63.11%, memory: 1.8%, duration: 237.63%
 * 
 * K: 100, per cluster: 20, error: 62.3%, memory: 1.73%, duration: 193.55%
 * 
 * K: 150, per cluster: 13, error: 58.66%, memory: 1.64%, duration: 286.79%
 * 
 * K: 200, per cluster: 10, error: 55.55%, memory: 1.84%, duration: 287.97%
 * 
 * K: 333, per cluster: 6, error: 47.85%, memory: 3.29%, duration: 320.91%
 * 
 * K: 500, per cluster: 4, error: 39.83%, memory: 6.6%, duration: 359.26%
 * 
 * K: 1000, per cluster: 2, error: 22.33%, memory: 25.16%, duration: 543.25%
 * 
 * 
 * ELEMENTS: 2000 DIM: 250:
 * 
 * K: 400, per cluster: 5.0, error: 50.35%, memory: 4.56%, duration: 398.92%
 * 
 * K: 200, per cluster: 10.0, error: 62.33%, memory: 2.01%, duration: 148.26%
 * 
 * K: 133, per cluster: 15.0, error: 67.31%, memory: 1.92%, duration: 186.22%
 * 
 * K: 100, per cluster: 20.0, error: 70.06%, memory: 2.17%, duration: 126.89%
 * 
 * K: 80, per cluster: 25.0, error: 71.7%, memory: 2.44%, duration: 106.04%
 * 
 * 
 * ELEMENTS: 4000, DIM: 250
 * 
 * K: 800, per cluster: 5.0, error: 50.17%, memory: 4.29%, duration: 324.74%
 * 
 * K: 400, per cluster: 10.0, error: 61.95%, memory: 1.54%, duration: 153.47%
 * 
 * K: 266, per cluster: 15.0, error: 67.02%, memory: 1.23%, duration: 146.95%
 * 
 * K: 200, per cluster: 20.0, error: 69.86%, memory: 1.25%, duration: 116.87%
 * 
 * K: 160, per cluster: 25.0, error: 71.58%, memory: 1.34%, duration: 111.93%
 * 
 * 
 * ELEMENTS: 10000, DIM: 50
 * 
 * K: 4000, per cluster: 2.5, error: 21.89%, memory: 16.04%, duration: 633.67%
 * 
 * K: 2000, per cluster: 5.0, error: 34.1%, memory: 4.09%, duration: 526.76%
 * 
 * K: 1000, per cluster: 10.0, error: 42.23%, memory: 1.15%, duration: 439.42%
 * 
 * 
 * 
 * 
 * INSIGHT: error is max for many dimensions and few elements. In this case, the
 * data space is very large but weakly populated
 */
public class AggregationDistanceMatrixTestResults {

}
