$(function() {
	var initPagination = function() {
		$(".pagination").pagination(count, {
			num_edge_entries : 1,
			num_display_entries : 10,
			callback : pageselectCallback,
			items_per_page : 10,
			current_page : currentpage
		});
	}();
});

function pageselectCallback(page_index, jq) {
	if ($("#current_page").val() != page_index) {
		$("#current_page").val(page_index);
		$("#form").submit();
	}
	return false;
}