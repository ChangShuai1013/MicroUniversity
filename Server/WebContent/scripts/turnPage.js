/*
  
  ������
    VIEW_SIZE ÿҳ��ʾ��������¼
    VIEW_INDEX ��ǰҳ����ţ���Ŵ�0��ʼ������1ҳ��VIEW_INDEX�� 0 ��
    LIST_SIZE �ܼ�¼��
    REQ ҳ���Ӧ�ĵ�url
        
  ����ֵ��
    ����һ��HTML�����ַ����������98%��
      
  ʹ�÷�����
    1.��ҳ���ļ������ô��ļ�����<script type="text/JScript" language="JScript" src="../script/turnPage.js"></script>
    2.���ʵ���λ�õ��ú�����getTurnPageRegion����
    <script language="javascript">
      document.write(getTurnPageRegion(<%=viewSize%>,<%=viewIndex%>,<%=listSize%>,"clerkSearchResult"));
    </script>
    
*/


var turnPageRegion =""  //�����ַ���
var pageCount  //��ҳ��
var VIEW_SIZE  //ÿҳ��ʾ��������¼
var VIEW_INDEX //��ǰҳ�����
var LIST_SIZE  //�ܼ�¼��
var REQ  //ҳ���Ӧ�ĵ�url
var PARAM

//������ҳ��
function getPageCount(){
  pageCount = LIST_SIZE / VIEW_SIZE
  pageCount = Math.floor(pageCount) 
  if(LIST_SIZE % VIEW_SIZE != 0) pageCount = pageCount+1
  return pageCount
}

//������������
function getNumberLink(){
  var numberLink = ""
  
  var style1 = "color: #000000;"
  var style2 = "color: blue;text-decoration: none;"
  var style = style1
  if(pageCount <= 1) 
    return numberLink
  else if(pageCount <= 7){
    for(i=1;i<pageCount+1;i++){
      if(i == VIEW_INDEX+1) 
        numberLink += i +"&nbsp;"
      else
        numberLink += "<a style='"+style+"' href='"+REQ+"?VIEW_SIZE="+VIEW_SIZE+"&VIEW_INDEX="+(i-1)+PARAM+"'>["+i+"]</a>&nbsp;"  
    }
  }
  else{
    if(VIEW_INDEX+1 <=4){
      for(i=1;i<=7;i++){
        if(i == VIEW_INDEX+1) 
          numberLink += i +"&nbsp;"
        else
          numberLink += "<a style='"+style+"' href='"+REQ+"?VIEW_SIZE="+VIEW_SIZE+"&VIEW_INDEX="+(i-1)+PARAM+"'>["+i+"]</a>&nbsp;"  
      }
    }
    else{
      h = pageCount
      if(VIEW_INDEX+1+3 <= pageCount)
        h = VIEW_INDEX+1+3
      k = 1
      do{
        if(h == VIEW_INDEX+1) 
          numberLink = h +"&nbsp;" +numberLink
        else          
          numberLink = "<a style='"+style+"' href='"+REQ+"?VIEW_SIZE="+VIEW_SIZE+"&VIEW_INDEX="+(h-1)+PARAM+"'>["+h+"]</a>&nbsp;"+numberLink  
        k++
        h--
      }while(k <= 7)  
    }    
  }
  numberLink = "&nbsp;" + numberLink 
  
  return numberLink
}

//�����ϱ߽�
function getLowIndex(){
  return  VIEW_INDEX * VIEW_SIZE
}

//�����±߽�
function getHighIndex(){
  var highIndex = (VIEW_INDEX + 1) * VIEW_SIZE
  if (LIST_SIZE < highIndex)
    highIndex = LIST_SIZE
  return highIndex	
}

//���ù�������
function setVar(VIEW_SIZE,VIEW_INDEX,LIST_SIZE,REQ,PARAM){
  this.VIEW_SIZE = VIEW_SIZE
  this.VIEW_INDEX = VIEW_INDEX
  this.LIST_SIZE = LIST_SIZE
  this.REQ = REQ
  this.pageCount = getPageCount()
  if(PARAM !=null && PARAM != "")
    this.PARAM = "&"+PARAM
  else
    this.PARAM = "" 
}

function getTurnPageRegion(VIEW_SIZE,VIEW_INDEX,LIST_SIZE,REQ){
  return getTurnPage(VIEW_SIZE,VIEW_INDEX,LIST_SIZE,REQ,null)
}

function getTurnPage(VIEW_SIZE,VIEW_INDEX,LIST_SIZE,REQ,PARAM){  
  setVar(VIEW_SIZE,VIEW_INDEX,LIST_SIZE,REQ,PARAM)
  
  if(pageCount == 0) 
    return "&nbsp;��������";
  
  turnPageRegion = ""+ 
  "<table width='98%' border=0 cellspacing='0' cellpadding='0' height='33' align='center'>"
  +"<tr>"
  
  //��1��TD
  +"<td width='27%' height='33'>"
  +"��<font color='blue'>"+(VIEW_INDEX+1)+"</font>/"+pageCount+"ҳ&nbsp;"
  
  +"&nbsp;<font color='blue'>"+(getLowIndex()+1) + "-" +getHighIndex()+"</font>/"+LIST_SIZE+"��" 
  +"</td>"
   
  //��2��TD
  +"<td width='9%' height='33' align='right'>"
  if(VIEW_INDEX > 0){  
    turnPageRegion +=""+ 
     "<img border='0' src='images/pageup.jpg' style='cursor:hand' title='��ҳ' onclick=window.location.href='"+REQ+"?VIEW_SIZE=" + VIEW_SIZE + "&VIEW_INDEX=0"+this.PARAM+"'>"
    +" <img border='0' src='images/back.jpg' style='cursor:hand' title='��һҳ' onclick=window.location.href='"+REQ+"?VIEW_SIZE=" + VIEW_SIZE + "&VIEW_INDEX=" + (VIEW_INDEX-1)+this.PARAM+"'>"
  }         
  turnPageRegion +=""
  +"</td>"
  
  //��3��TD
  +"<td width='36%' height='33' align='center'>"  
  turnPageRegion += getNumberLink() 
  turnPageRegion +=""
  +"</td>"
  
  //��4��TD
  +"<td width='9%' height='33'>"
  if(VIEW_INDEX < pageCount-1){  
    turnPageRegion +=""+  
    "<img border='0' src='images/next.jpg' style='cursor:hand' title='��һҳ' onclick=window.location.href='"+REQ+"?VIEW_SIZE=" + VIEW_SIZE + "&VIEW_INDEX="+(VIEW_INDEX+1)+this.PARAM+"'>"
    +" <img border='0' src='images/pagedown.jpg' style='cursor:hand' title='βҳ' onclick=window.location.href='"+REQ+"?VIEW_SIZE=" + VIEW_SIZE + "&VIEW_INDEX=" + (pageCount-1)+this.PARAM+"'>"
   }          
   turnPageRegion +=""+
   "</td>"
   
   //��5��TD
   +"<td width='21%' height='33' align='center'>"
   if(pageCount > 1){
     turnPageRegion +=""
     +"&nbsp;&nbsp;ת�� "
     +"<input type='text' id='toThePage' size='2' style='border-style: solid; border-width: 1'>"
     +" ҳ "
     +"<img border='0' src='images/go.jpg' style='cursor:hand' title='��ת' onclick='goToPage()'>"	
   }else
     turnPageRegion +="&nbsp;"
    
    turnPageRegion +=""
    +"</td>"
    
    +"</tr>"
    +"</table>"
    
    return turnPageRegion
}

//"��ת"
function goToPage(){
  var toThePage = document.getElementById("toThePage")
  var v = toThePage.value
  if(!isNumberString(v,"0123456789")){
    alert("���������֣�")
    toThePage.select()
    return;
  }
  
  if(v > pageCount || v == 0){
    alert("����ҳ�뷶Χ��")
    toThePage.select()
    return;
  }
    
  v = v.replace(/(^\s*)|(\s*$)/g, "")
  v = v-1
  window.location.href=""+REQ+"?VIEW_SIZE=" + VIEW_SIZE + "&VIEW_INDEX="+v+PARAM
}

//�ж����봮�Ƿ�Ϸ�����
function isNumberString (InString,RefString)
{       
  var xsdgs=0
  if(InString.length==0) return false
  
  for (Count=0; Count < InString.length; Count++)  
  {
    TempChar= InString.substring (Count, Count+1)
    if (RefString.indexOf (TempChar, 0)==-1)  
      return false
    else if(TempChar==".")
    {  
      xsdgs=xsdgs+1
      if (xsdgs>1) return false    //С���㳬��һ��
      if (Count==InString.length-1) return false  //С���������һλ 
    }   
  }	
  return true
}