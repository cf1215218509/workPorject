<!-- script代码，如果需要引用js文件，请使用与HTML中相同的方式。 -->
<script type="text/javascript">

$(document).click(function(){

var a  = $("#field20567").val();

		var b1 = $("#field20571_0span").text();
               var b2 = $("#field20572_0span").text();
               var b3 = $("#field20573_0span").text();
               var b4 = $("#field20574_0span").text();
                      if(a!=""){
			if(a==0){				 				                                                                                     
                             c1 =b1.substring(0, b1.lastIndexOf('x'));                            
                             c2 =b2.substring(0, b2.lastIndexOf('x'));                            
                             c3 =b3.substring(0, b3.lastIndexOf('x'));                             
                             c4 =b4.substring(0, b4.lastIndexOf('x'));
                             if(c1==""){
                                 $("#field20571_0span").empty();
                                 checkinput ("field20571_0__","field20571_0spanimg");
                                 $("#field20571_0spanimg").show(); 
                            }
                             if(c2==""){
                                 $("#field20572_0span").empty();
                                 checkinput("field20572_0__","field20572_0spanimg");
                                 $("#field20572_0spanimg").show(); 
                            }
                             if(c3==""){
                                $("#field20573_0span").empty();
                                checkinput("field20573_0__","field20573_0spanimg");
                                $("#field20573_0spanimg").show(); 
                            }
                             if(c4==""){
                                $("#field20574_0span").empty();
                                checkinput("field20574_0__","field20574_0spanimg");
                                $("#field20574_0spanimg").show(); 
                            }
                                                   						
		}else{  
                         $("#field20571_0spanimg").hide();     
                         $("#field20572_0spanimg").hide();  
                         $("#field20573_0spanimg").hide();   
                         $("#field20574_0spanimg").hide();                                                             
			 
                         v3 =b1.substring(0, b1.lastIndexOf('x'));
                         $("#field20573_0span").text(v3);
                        
                         v4 =b2.substring(0, b2.lastIndexOf('x'));
                         $("#field20574_0span").text(v4);
		}
	
          }
	
});
					
</script>