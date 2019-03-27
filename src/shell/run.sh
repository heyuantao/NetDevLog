echo "Delete old dir !"
hdfs dfs -rm -r -skipTrash /output/srx240h.output
echo "Begin Process file !"
spark-submit --class Preprocess ../../out/artifacts/NetDevLog_jar/NetDevLog.jar yarn hdfs://clouderanode01.syslab.org/upload/srx240h*  hdfs://clouderanode01.syslab.org/output/srx240h.output
