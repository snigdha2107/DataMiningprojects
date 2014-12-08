Question 1:

The solution is in KMeans zip file .

To run it wine.data K as command line argument we need to provide.


To run question 2 & 3 I have incorporated the first question code along with finding the centroids 
and the error rate.
to run it just put in command line wine.data.

Question 2:
I am getting an error rate of 2.53 approximately which varies as we change the random centroid values.
error rate for inter is 35.13.
error rate for Exter is 13.92

The major factors that can impact the performance of the K-means algorithm are the following:
1. Choosing the initial centroids.
2. Estimating the number of clusters K.
 
-> Issue of our concern is that K-Means results are highly dependent on the initial centroids

-> The drawback in the heart of this project is that this algorithm gives the same level of relevance to all the features in a dataset. 

Question 3:
Radius is nothing but the centroid which I have calculated for k=1,2,3,4 & 5 .

This is the average I got.
Centroid have been now 
     (26.42, 50.64)
     (31.00, 35.16)
     (33.58, 17.57)

A K-Means clusterer will generally produce good results if the overlap between the clusters
 is minimal and if each cluster exhibits variability that is uniform in all directions.
  When the data variability is different along the different directions in the data space, 
  the results you obtain with a K-Means clusterer may be improved by first normalizing the 
  data appropriately, as can be done in this module when you set the do_variance_normalization
   option in the constructor. However, as pointed out elsewhere in this documentation, 
   such normalization may actually decrease the performance of the clusterer if the 
   overall data variability along any dimension is more a result of separation between 
   the means than a consequence of intra-cluster variability.
So depends on the variability.

Question 4:

Algorithm 1 deals with modifying existing k means algorithm

Step 1 : Randomly choose k initial seeds as a cluster centers

Step 2 : For I <-1 to m do compute min© and min(xi,C)

Step 3 :  If min(xi,C)<=min© then go to sept 6

Step 4 : Assign Xi to be the center of a new cluster Ck and set k’=k’+1

Step 5 : If k’>kmax, merge the two closest clusters into one cluster ans set k’=k max

Step 6 : Assign xi to its nearest cluster.

Step 7 : Go to step 2 until the cluster member is stabilized.

Algorithm 2 deals with finding outliers

Step 1 : Construct an MST by the centroids of set C and add it to F

Step 2 : Remove the longest edge from the tree in F and replace the tree with two newly obtained 

subtrees.

Step 3 : Regard the clusters in the small subtree as outliers.


Reference :
http://sci2s.ugr.es/docencia/doctoM6/TwoPhaseClusteringDetectionOutliers.pdf