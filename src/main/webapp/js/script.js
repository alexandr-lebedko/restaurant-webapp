function previewImage(event, destinationImageId) {
    document.getElementById(destinationImageId).src = URL.createObjectURL(event.target.files[0]);
}

function post(path, params) {
    sendRequest(path, 'post', params);
}

function deleteItemRow(rowSelector) {
    const dishRow = document.querySelector("#".concat(rowSelector));

    dishRow.parentNode.removeChild(dishRow);
}

function minusItemBasket(dishAmountSelector) {
    const dishDiv = document.querySelector("#".concat(dishAmountSelector));

    if (dishDiv.innerText > 1) {
        dishDiv.innerText--;
    }
}

function plusItemBasket(dishAmountSelector) {
    const dishDiv = document.querySelector("#".concat(dishAmountSelector));

    if (isNaN(dishDiv.innerText)) {
        dishDiv.innerText = 1;
    }
    const value = dishDiv.innerText++;
}

function submitOrder() {
    const form = document.querySelector("form");
    console.log(form);

    const itemIds = document.querySelectorAll(".item-id");
    const itemAmounts = document.querySelectorAll(".item-amount");

    for (let k = 0; k < itemIds.length; k++) {
        const itemIdInput = document.createElement('input');
        itemIdInput.name = "item-id";
        itemIdInput.value = itemIds[k].innerText;
        itemIdInput.type = "hidden";

        const itemAmountInput = document.createElement('input');
        itemAmountInput.name = "item-amount";
        itemAmountInput.value = itemAmounts[k].innerText;
        itemAmountInput.type = "hidden";

        form.appendChild(itemIdInput);
        form.appendChild(itemAmountInput);
    }
    form.submit();
}

function get(path, params) {
    sendRequest(path, 'get', params);
}

function sendRequest(path, method, params) {
    const form = document.createElement("form");

    form.setAttribute("action", path);
    form.setAttribute("method", method);

    for (const key in params) {
        if (params.hasOwnProperty(key)) {
            const field = document.createElement("input");
            field.setAttribute("type", "hidden");
            field.setAttribute("name", key);
            field.setAttribute("value", params[key]);

            form.appendChild(field);
        }

        document.body.appendChild(form);
        form.submit();
    }
}

$('#order-content-form')
    .each(function () {
        $(this).data('initial-form-data', $(this).serialize())
    })
    .on('change input', function () {
        $(this)
            .find('input:submit, button:submit')
            .prop('disabled', $(this).serialize() == $(this).data('initial-form-data'));
    })
    .find('input:submit, button:submit')
    .prop('disabled', true);

function deleteOrderItemRow(rowId) {
    const selector = "#".concat(rowId);

    if ($(selector).siblings().length > 0) {

        $(selector).remove();

        $('#order-content-form')
            .find('button:submit')
            .prop('disabled', false);
    }
}

$(document).ready(function () {
    var hiddenRows = $('tr.order-content');
    $('.info-btn').click(function () {
        var hiddenRow = $(this).parent().parent().next().toggle();
        hiddenRows.not(hiddenRow).hide();
    })
})

$(document).ready(function () {
    $('#categoryModal').on('shown.bs.modal', function (event) {

        var button = $(event.relatedTarget);

        var uaTitle = button.data('ua');
        var enTitle = button.data('en');
        var ruTitle = button.data('ru');
        var id = button.data('id');
        var modal = $(this);

        modal.find('#ruTitle').val(ruTitle);
        modal.find('#enTitle').val(enTitle);
        modal.find('#uaTitle').val(uaTitle);
        modal.find('#categoryId').val(id);
        modal.find('#deleteId').val(id);

    });

    $('.modal').on('hide.bs.modal', function (e) {
        $('#category-alert').remove();
        var modal = $(this);

        modal.find('.ruTitle').val('');
        modal.find('.enTitle').val('');
        modal.find('.uaTitle').val('');
        modal.find('.categoryId').val('');
    });
})

