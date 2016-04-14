/**
 * 
 */
$(document).ready(function() {
	$(".testme").click(function() {
		$.ajax({
			url : $(this).attr('href'),
			success : function(html) {
				$("#getmyfiles").html(html);
				$("#light").show();
			
			}
		})
		return false;
	})

});

var currentId;
$('body').on('contextmenu','.fileme',function(event) {
	currentId = $(this).attr('file-id');
	// Avoid the real one
	event.preventDefault();

	// Show contextmenu
	$(".custom-menu").finish().toggle(100).

	// In the right position (the mouse)
	css({
		top : event.pageY + "px",
		left : event.pageX + "px"
	});
});

// If the document is clicked somewhere
$(document).bind("mousedown", function(e) {

	// If the clicked element is not the menu
	if (!$(e.target).parents(".custom-menu").length > 0) {

		// Hide it
		$(".custom-menu").hide(100);
	}
});

// If the menu element is clicked
$(".custom-menu li").click(function() {

	// This is the triggered action name
	switch ($(this).attr("data-action")) {

	// A case for each action. Your actions here
	case "first":
		window.open("open?fileid=" + currentId, "_self");
		break;
	case "second":
		window.open("DBFile?flag=delete&fileid=" + currentId,"_self");
		break;
		case "third":
		$('#renamepop').val(currentId);
		break;
	case "fourth":
		window.open("webdown?fileid=" + currentId);
		break;
	}

	// Hide it AFTER the action was triggered
	$(".custom-menu").hide(100);
});
var folderId;

$('.testme').bind("contextmenu", function(event) {
	folderId = $(this).attr('folder-id');
	// Avoid the real one
	event.preventDefault();

	// Show contextmenu
	$(".foldermenu").finish().toggle(100).

	// In the right position (the mouse)
	css({
		top : event.pageY + "px",
		left : event.pageX + "px"
	});
});

// If the document is clicked somewhere
$(document).bind("mousedown", function(e) {

	// If the clicked element is not the menu
	if (!$(e.target).parents(".foldermenu").length > 0) {

		// Hide it
		$(".foldermenu").hide(100);
	}
});

// If the menu element is clicked
$(".foldermenu li").click(function() {

	// This is the triggered action name
	switch ($(this).attr("data-action")) {

	// A case for each action. Your actions here
	case "once":
		$('#inviteguest').val(folderId);
		break;
	}

	// Hide it AFTER the action was triggered
	$(".foldermenu").hide(100);
});
$('#update').on('shown.bs.modal', function() {
	$('#myInput').focus();
});
$('#newfolder').on('shown.bs.modal', function() {
	$('#myInput').focus();
});

$(init);
function init() {
	$('#light').draggable({
		cursor : 'move'
	});
}