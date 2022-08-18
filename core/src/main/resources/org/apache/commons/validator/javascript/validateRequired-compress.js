function validateRequired(_1){
var _2=true;
var _3=null;
var i=0;
var _5=new Array();
var _6=eval("new "+jcv_retrieveFormName(_1)+"_required()");
for(var x in _6){
if(!jcv_verifyArrayElement(x,_6[x])){
continue;
}
var _8=_1[_6[x][0]];
if(!jcv_isFieldPresent(_8)){
_5[i++]=_6[x][1];
_2=false;
}else{
if((_8.type=="hidden"||_8.type=="text"||_8.type=="textarea"||_8.type=="file"||_8.type=="radio"||_8.type=="checkbox"||_8.type=="select-one"||_8.type=="password")){
var _9="";
if(_8.type=="select-one"){
var si=_8.selectedIndex;
if(si>=0){
_9=_8.options[si].value;
}
}else{
if(_8.type=="radio"||_8.type=="checkbox"){
if(_8.checked){
_9=_8.value;
}
}else{
_9=_8.value;
}
}
if(trim(_9).length==0){
if((i==0)&&(_8.type!="hidden")){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
if(_8.type=="select-multiple"){
var _b=_8.options.length;
lastSelected=-1;
for(loop=_b-1;loop>=0;loop--){
if(_8.options[loop].selected){
lastSelected=loop;
_9=_8.options[loop].value;
break;
}
}
if(lastSelected<0||trim(_9).length==0){
if(i==0){
_3=_8;
}
_5[i++]=_6[x][1];
_2=false;
}
}else{
if((_8.length>0)&&(_8[0].type=="radio"||_8[0].type=="checkbox")){
isChecked=-1;
for(loop=0;loop<_8.length;loop++){
if(_8[loop].checked){
isChecked=loop;
break;
}
}
if(isChecked<0){
if(i==0){
_3=_8[0];
}
_5[i++]=_6[x][1];
_2=false;
}
}
}
}
}
}
if(_5.length>0){
jcv_handleErrors(_5,_3);
}
return _2;
}

