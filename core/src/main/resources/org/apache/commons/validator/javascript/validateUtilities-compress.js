function jcv_retrieveFormName(_1){
var _2;
if(_1.getAttributeNode){
if(_1.getAttributeNode("id")&&_1.getAttributeNode("id").value){
_2=_1.getAttributeNode("id").value;
}else{
_2=_1.getAttributeNode("name").value;
}
}else{
if(_1.getAttribute){
if(_1.getAttribute("id")){
_2=_1.getAttribute("id");
}else{
_2=_1.attributes["name"];
}
}else{
if(_1.id){
_2=_1.id;
}else{
_2=_1.name;
}
}
}
_2=_2.replace(/:/gi,"_");
return _2;
}
function jcv_handleErrors(_3,_4){
if(_4&&_4!=null){
var _5=true;
if(_4.disabled||_4.type=="hidden"){
_5=false;
}
if(_5&&_4.style&&_4.style.visibility&&_4.style.visibility=="hidden"){
_5=false;
}
if(_5){
_4.focus();
}
}
alert(_3.join("\n"));
}
function jcv_verifyArrayElement(_6,_7){
if(_7&&_7.length&&_7.length==3){
return true;
}else{
return false;
}
}
function jcv_isFieldPresent(_8){
var _9=true;
if(_8==null||(typeof _8=="undefined")){
_9=false;
}else{
if(_8.disabled){
_9=false;
}
}
return _9;
}
function jcv_isAllDigits(_a){
_a=_a.toString();
var _b="0123456789";
var _c=0;
if(_a.substring(0,2)=="0x"){
_b="0123456789abcdefABCDEF";
_c=2;
}else{
if(_a.charAt(0)=="0"){
_b="01234567";
_c=1;
}else{
if(_a.charAt(0)=="-"){
_c=1;
}
}
}
for(var n=_c;n<_a.length;n++){
if(_b.indexOf(_a.substring(n,n+1))==-1){
return false;
}
}
return true;
}
function jcv_isDecimalDigits(_e){
_e=_e.toString();
var _f="0123456789";
var _10=0;
if(_e.charAt(0)=="-"){
_10=1;
}
for(var n=_10;n<_e.length;n++){
if(_f.indexOf(_e.substring(n,n+1))==-1){
return false;
}
}
return true;
}
function trim(s){
return s.replace(/^\s*/,"").replace(/\s*$/,"");
}
if(typeof (console)!=="undefined"&&typeof (console.warn)==="function"){
console.warn("The JS part of commons validation is deprecated. "+"Please consider using http://parsleyjs.org/ or another "+"validation library.");
}

