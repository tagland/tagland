curl http://inthroughthediorama.blogspot.com/feeds/posts/default  | tidy -xml -i -q -wrap 255 > tmp-backup.xml

echo Copy tmp-backup.xml to a new file and add to svn.  
