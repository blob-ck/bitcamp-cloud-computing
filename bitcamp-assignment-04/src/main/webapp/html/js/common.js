'use strict'

var serverApiAddr = "http://localhost:8080/bitcamp-assignment-04"


/*window.onload = () => {
    console.log('window onload ......')
};
$(document.body).ready(() => {
    console.log('document.body ready().....');
});
*/
$(() => {
    $('footer').load(`${serverApiAddr}/html/footer.html`);
});