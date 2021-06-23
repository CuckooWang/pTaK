# pTaK
Phosphoproteomics-based targeted kinome profiling

## The description of each source code and file
### RawDataNormalization.java
Normalization of the phosphoproteomic data generated by MaxQuant.
### Quartile.java
Quantile analysis used to remove outliers.
### Chisquare.java
chi-squared test was adopted to determine the kinases that significantly activated or inbibited in HCC samples.
### HCC1_rawdata.txt & NAT1_rawdata.txt
These files contain the raw intensities of p-sites from HCC1 and NAT1 samples quantified by MaxQuant.
### HCC1_norm.txt & NAT1_norm.txt
These files contain the normalized intensities of p-sites from HCC1 and NAT1 samples.
### psite_ELM.igps.txt
Site-specific kinase-substrate relations (ssKSRs) predicted by iGPS 1.0 (http://igps.biocuckoo.org/).
### Chisuqare_out.txt
The results of kinases that significantly activated or inhibited in HCC1 samples calculated by Chisquanre.java.

## Software Requirements
### OS Requirements
Above codes have been tested on the following systems:  
Windows: Windows 7, Windos 10  
Linux: CentOS linux 7.8.2003  
iGPS 1.0 for windows platform were downloaded and adopted before pTaK
### Hardware Requirements
All codes and softwares could run on a "normal" desktop computer, no non-standard hardware is needed

## Installation guide
All codes can run directly on a "normal" computer with JAVA 1.8.0 installed, no extra installation is required

## Additional information
Expected run time is depended on the number of phosphosites used for prediction, it will take about 5 minutes for 2,000 sites.
## Contact
Dr. Yu Xue: xueyu@hust.edu.cn  
Dr. Ping Xu: xuping@mail.ncpsb.org 
Chenwei Wang: wangchenwei@hust.edu.cn
Yao Zhang: zhangyaowsw@163.com


