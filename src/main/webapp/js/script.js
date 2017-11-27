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

    $('.category-modal').on('hide.bs.modal', function (e) {
        $('#category-alert').remove();
        var modal = $(this);

        modal.find('.ruTitle').val('');
        modal.find('.enTitle').val('');
        modal.find('.uaTitle').val('');
        modal.find('.categoryId').val('');
    });

    $('#image').on('change', function () {
        $('#image-preview').attr('src', window.URL.createObjectURL(this.files[0]))
        $('#image-preview').css({"display": "inline"})
    })

    $('#image-modal').on('shown.bs.modal', function (e) {
        const image = $(e.relatedTarget)
        const id = image.data('id')
        const modal = $(this)
        modal.find('#updateImageItemId').val(id)
    })

    $('#itemModal').on('hide.bs.modal', function () {
        const modal = $(this);
        modal.find('#item-alert').remove()
        modal.find('#itemId').val('');
        modal.find('.uaTitle ').val('')
        modal.find('.enTitle').val('')
        modal.find('.ruTitle').val('')
        modal.find('.uaDescription').val('')
        modal.find('.ruDescription').val('')
        modal.find('.enDescription').val('')
        modal.find('#price').val('')
    })

    $('#item-modal').on('shown.bs.modal', function (e) {

        const row = $(e.relatedTarget);
        const id = row.data('id')
        const uaTitle = row.data('title-ua');
        const enTitle = row.data('title-en');
        const ruTitle = row.data('title-ru');

        const uaDescription = row.data('description-ua')
        const ruDescription = row.data('description-ru')
        const enDescription = row.data('description-en')
        const category = row.data('category')
        const price = row.data('price')
        const imageId = row.data('image')

        const modal = $(this);

        modal.find('#itemId').val(id);
        modal.find('.uaTitle ').val(uaTitle)
        modal.find('.enTitle').val(enTitle)
        modal.find('.ruTitle').val(ruTitle)
        modal.find('.uaDescription').val(uaDescription)
        modal.find('.ruDescription').val(ruDescription)
        modal.find('.enDescription').val(enDescription)
        modal.find('#price').val(price)
        modal.find('#imageId').val(imageId)

        modal.find('#' + category).prop('selected', true);
    });

    $('.delete-row-button.NEW').on('click', function (e) {
        if ($(this).closest("tr").siblings().length > 0) {
            $(this).closest("tr").remove()
        }
    })

    $('.row-link').on('click', function () {
        location.href = $(this).data('url')
    })

    // add item to order bucket request
    $('.item-card').on('click', function (e) {
        var url = $(this).data('url')
        var attr = $(this).data('attr')
        var value = $(this).data('value')

        var form = $('<form>', {
            action: url,
            method: 'post'
        }).append($('<input>', {
                type: 'hidden',
                name: attr,
                value: value
            }
        ));
        $(document.body).append(form)
        form.submit();
    })
})

