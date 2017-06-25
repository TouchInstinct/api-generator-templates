$(function () {
    $(document).ready(function () {

        $('.part-example-response .text-response').css('height', $('.inputs').height() - 21);

        $(document).on('click', 'menu li', function (e) {
            e.stopPropagation();

            $(this).closest('menu').find('li').removeClass('active');
            $(this).addClass('active');
        });

        $(document).on('click', '.show-example-response', function () {
            $(this)
                .closest('.table')
                .toggleClass('example-response-expanded');
        });

        $(document).on('focus', '.search-block input', function () {
            $(this).attr('placeholder', '');
        });


        $(document).on('blur', '.search-block input', function () {
            $(this).attr('placeholder', 'Поиск');
        });

        $(document).on('click', '.styled-input .null-btn', function () {
            $(this)
                .closest('.styled-input')
                .find('input')
                .val('NULL');
        });

        $(document).on('focus', '.disabled input', function () {
            $(this)
                .closest('label')
                .removeClass('disabled');
        });

        $(document).on('click', '.run-btn', function (e) {
            var model = serializeInputs('.inputs');

            console.log('Send data: ' + JSON.stringify(model));

            $.ajax({
                    type: 'POST',
                    data: JSON.stringify(model),
                    contentType: 'application/json',
                    dataType: 'json',
                    url: 'json/response.json'
                })
                .done(
                    function (response) {
                        var json = JSON.stringify(response, null, 4);
                        json = highlightJson(json);

                        $('.text-response').html(json);
                    })
                .fail(
                    function () {
                        alert('Ошибка запроса.');
                    });
        });


        $(document).on('keyup', '.search-block input', function (e) {
            var $searchInput = $(this);
            var $typeaheadContainer = $searchInput.closest('.search-block').find('.typeahead-container');
            var $typeaheadContent = $typeaheadContainer.find('.typeahead-content');

            $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    url: 'json/methods.json'
                })
                .done(
                    function (response) {
                        var itemsLink = '';
                        response
                            .filter(function (item) {
                                return item.name.toLowerCase().indexOf($searchInput.val().toLowerCase()) >= 0;
                            })
                            .forEach(function (item) {
                                itemsLink += '<a href="' + item.url + '">' + item.name + '</a>'
                            });
                        $typeaheadContent.html(itemsLink);

                        if (itemsLink != '') {
                            $typeaheadContainer.removeClass('typeahead-container--hide');
                        } else {
                            $typeaheadContainer.addClass('typeahead-container--hide');
                        }
                    })
                .fail(
                    function () {
                        alert('Ошибка запроса.');
                    });

        });

        $(document).on('blur', '.search-block input', function (e) {
            $(this).closest('.search-block').find('.typeahead-container').addClass('typeahead-container--hide');
        });

        $(document).on('click', '.styled-input .clear-btn', function (e) {
            e.preventDefault();
            $(this)
                .closest('label')
                .addClass('disabled')
                .find('input')
                .val('');
        });

        function serializeInputs(containerClass) {
            var model = {};
            $(containerClass + ' label:not(.disabled) input,' + containerClass + ' label:not(.disabled) select')
                .each(function () {
                    model[$(this).data('name')] = $(this).val() == 'NULL' ? null : $(this).val();
                });

            return model;
        }

        function highlightJson(jsonString) {
            jsonString = jsonString.replace(/(".*":\s)(".*")/ig, "$1<span class='text-string'>$2</span>");
            jsonString = jsonString.replace(/(".*":\s)(\d*)/ig, "$1<span class='text-digit'>$2</span>");

            return jsonString
        }
    });
});