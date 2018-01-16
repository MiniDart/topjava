var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
    prepareInputEnabled();
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
       prepareInputEnabled()
    });
}

function prepareInputEnabled() {
    $("input[name=enabled]").change(function () {
        var enabled;
        if (this.checked){
            enabled="true"
        }
        else enabled="false";
        $.ajax({
            type: "POST",
            url: ajaxUrl,
            data: {enabled: enabled,
            id:$(this).closest("tr").attr("id")},
            success: function () {
                updateTable();
            }
        })
    });
}