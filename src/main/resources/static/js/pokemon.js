function addPokemon(event) {
    event.preventDefault();

    var formData = new FormData(document.getElementById('pokemonForm'));
    console.log("送信されるデータ:");
        for (var pair of formData.entries()) {
            console.log(pair[0] + ": " + pair[1]); // フィールド名とその値を表示
        }

    var xhr = new XMLHttpRequest();

    xhr.open('POST', '/pokemon/newnames', true);

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            var response = JSON.parse(xhr.responseText);
            document.getElementById('messageContainer').textContent = response.message;
            loadPokemonList();
        } else if (xhr.status === 400) {
            var response = JSON.parse(xhr.responseText);
            document.getElementById('messageContainer').textContent = response.message;
        } else {
            document.getElementById('messageContainer').textContent = 'エラーが発生しました。';
        }
    };

    xhr.send(formData);
}

function loadPokemonList() {

    $.ajax({
        url: '/pokemon/listnames',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log("ポケモンリスト取得成功:", data);

            var pokemonTableBody = $('#pokemonTableBody');
            pokemonTableBody.empty();

            data.forEach(function(pokemon) {
                var row = $('<tr>');
                        row.append('<td>' + pokemon.id + '</td>');
                var nameInput = $('<input>', {type: 'text',id: 'name-' + pokemon.id,class: 'pokemon-name',value: pokemon.name,'data-id': pokemon.id,});
                        row.append($('<td>').append(nameInput));
                var type1Select = $('<select>', {class: 'pokemon-type1','data-id': pokemon.id,});
                        type1Select.append(getTypeOptions(pokemon.type1));
                        row.append($('<td>').append(type1Select));
                var type2Select = $('<select>', {class: 'pokemon-type2','data-id': pokemon.id,});
                        type2Select.append('<option value=""></option>');
                        type2Select.append(getTypeOptions(pokemon.type2));
                        row.append($('<td>').append(type2Select));
                    row.append('<td><button type="button" class="update-button" data-id="' + pokemon.id + '">更新</button></td>');
                    row.append('<td><button type="button" class="delete-button" data-id="' + pokemon.id + '">削除</button></td>');

                pokemonTableBody.append(row);
            });

                $('.update-button').on('click', function(event) {
                    var pokemonId = $(this).data('id');
                    updatePokemon(event, pokemonId);
                        });

                $('.delete-button').on('click', function(event) {
                    var pokemonId = $(this).data('id');
                        deletePokemon(event, pokemonId);
                        });
                    },

        error: function(xhr, status, error) {
            console.error("AJAXエラー:", status, error);
            alert('ポケモン一覧の取得に失敗しました。');
        }
    });
}

function updatePokemon(event, pokemonId) {
    event.preventDefault();

    var name = document.getElementById('name-' + pokemonId).value;
    var type1 = document.querySelector('.pokemon-type1[data-id="' + pokemonId + '"]').value;
    var type2 = document.querySelector('.pokemon-type2[data-id="' + pokemonId + '"]').value;

    var data = 'name=' + encodeURIComponent(name) +
                   '&type1=' + encodeURIComponent(type1) +
                   '&type2=' + encodeURIComponent(type2);

    var xhr = new XMLHttpRequest();
    xhr.open('PATCH', '/pokemon/updatenames/' + pokemonId, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            var response = JSON.parse(xhr.responseText);
            document.getElementById('messageContainer').textContent = response.message;
            loadPokemonList();
        } else {
            document.getElementById('messageContainer').textContent = 'エラーが発生しました。';
        }
    };

    xhr.send(data);
}

function deletePokemon(event, pokemonId) {
    event.preventDefault();

    var xhr = new XMLHttpRequest();

    xhr.open('DELETE', '/pokemon/deletenames/' + pokemonId, true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            var response = JSON.parse(xhr.responseText);
            document.getElementById('messageContainer').textContent = response.message;
            loadPokemonList();
        } else {
            document.getElementById('messageContainer').textContent = 'エラーが発生しました。';
        }
    };

    xhr.send('_method=DELETE');
}

document.addEventListener("DOMContentLoaded", function() {

    fetch("/pokemon/type")
        .then(response => response.json())
        .then(types => {
            const type1Select = document.getElementById("type1");
            const type2Select = document.getElementById("type2");

            types.forEach(type => {
                const option = document.createElement("option");
                option.value = type.type;
                option.textContent = type.type;
                type1Select.appendChild(option);
            });

            types.forEach(type => {
                const option = document.createElement("option");
                option.value = type.type;
                option.textContent = type.type;
                type2Select.appendChild(option);
            });
        })
        .catch(error => {
            console.error("エラーが発生しました。", error);
        });
});

function loadTypes() {
    $.ajax({
        url: '/pokemon/type',
        type: 'GET',
        dataType: 'json',
        success: function(types) {

            var type1Select = $('#type1');
            var type2Select = $('#type2');

            types.forEach(function(type) {
                var option = $('<option>').val(type.id).text(type.type);
                type1Select.append(option.clone());
                type2Select.append(option);
            });
        },
        error: function(error) {
            console.error('タイプデータの取得に失敗しました:', error);
        }
    });
}

function getTypeOptions(selectedTypeId) {
    var typeOptions = '';
    $.ajax({
        url: '/pokemon/type',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function(types) {
            types.forEach(function(type) {
                var selected = (type.type == selectedTypeId) ? ' selected' : '';
                typeOptions += '<option value="' + type.type + '"' + selected + '>' + type.type + '</option>';
            });
        }
    });
    return typeOptions;
}
