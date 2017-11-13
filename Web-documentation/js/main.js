$(function () {
    $(document).ready(function () {
        let sizes = [30, 70];

        const splitSizesKey = 'split-sizes';

        try {
            const localStorageSizes = localStorage.getItem(splitSizesKey);
            sizes = JSON.parse(localStorageSizes);
        } catch (e) {
            console.info("Unable to read split size from localStorage. Using defaults.");
        }

        let split = window.Split(['.aside-left', '.aside-right'], {
            sizes: sizes,
            minSize: [200, 769],
            onDragEnd: function () {
                try {
                    sizes = split.getSizes();
                    localStorage.setItem(splitSizesKey, JSON.stringify(sizes));
                } catch (e) {
                    // SecurityError (DOM Exception 18): The operation is insecure.
                }
            }
        });

        window.split1 = split;

        const beforePrint = function() {
            split.collapse(0);
            console.log('Functionality to run before printing.');
        };
        const afterPrint = function() {
            split.setSizes(sizes);
            console.log('Functionality to run after printing.');
        };

        if (window.matchMedia) {
            const mediaQueryList = window.matchMedia('print');
            mediaQueryList.addListener(function(mql) {
                if (mql.matches) {
                    beforePrint();
                } else {
                    afterPrint();
                }
            });
        }

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
            var serverMethodPath = $(this).data('serverMethodPath')

            console.log('Send data: ' + JSON.stringify(model));

            $.ajax({
                    type: 'POST',
                    data: JSON.stringify(model),
                    contentType: 'application/json',
                    dataType: 'json',
                    url: serverMethodPath
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
            var relativeUrl = $searchInput.data('relativeToRootPath');
            var $typeaheadContainer = $searchInput.closest('.search-block').find('.typeahead-container');
            var $typeaheadContent = $typeaheadContainer.find('.typeahead-content');

            var itemsLink = '';
            searchItems
                .filter(function (item) {
                    return item.name.toLowerCase().indexOf($searchInput.val().toLowerCase()) >= 0;
                })
                .forEach(function (item) {
                    itemsLink += '<a href="' + (relativeUrl ? relativeUrl + "/" : "") + item.url + '">' + item.name + '</a>'
                });
            $typeaheadContent.html(itemsLink);

            if (itemsLink != '') {
                $typeaheadContainer.removeClass('typeahead-container--hide');
            } else {
                $typeaheadContainer.addClass('typeahead-container--hide');
            }

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