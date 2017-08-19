$(document).ready(function () {
    var contentHeight = $("#admin-middle-section").height();
    console.log("CONTENT HEIGHT: " + contentHeight);
    $('#side-bar').height(contentHeight);
}).get();

function previewImage(event, destinationImageId) {
    document.getElementById(destinationImageId).src = URL.createObjectURL(event.target.files[0]);
}