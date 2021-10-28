let render = new Render();
let csrfToken;

let queryEntity = {
    year: '',
    band: '',
    order: 'a.currentRating'
}

function getAllAlbums() {
    $('#user').empty();
    if ($.cookie('jwtToken') != null && $.cookie('userName') != null) {
        $('#hello').removeClass('hiddenElement');
        $('#logoutForm').removeClass('hiddenElement');
        $('#loginForm').addClass('hiddenElement');
        render.getUserName();
    }
    $.ajax({
        url: '/albums/allAlbums',
        type: 'get',
        data: queryEntity,
        headers: {'X-CSRF-Token': csrfToken},
        success: [function (data, status, response) {
            csrfToken = response.getResponseHeader('X-CSRF-Token');
            render.renderAlbums(data)
        }]
    })
}

$('#select_all').click(function () {
    $('#select_by_band_name').val('');
    $('#select_by_release_year').val('');
    $('.button').removeClass('selectedOrder');
    $('#rating').addClass('selectedOrder');
    queryEntity.year = '';
    queryEntity.band = '';
    queryEntity.order = 'a.currentRating';
    getAllAlbums();
});

$('#album').click(function () {
    $('.button').removeClass('selectedOrder');
    $('#album').addClass('selectedOrder');
    queryEntity.order = 'a.albumName';
    getAllAlbums();
});

$('#year').click(function () {
    $('.button').removeClass('selectedOrder');
    $('#year').addClass('selectedOrder');
    queryEntity.order = 'a.releaseYear';
    getAllAlbums();
});

$('#band').click(function () {
    $('.button').removeClass('selectedOrder');
    $('#band').addClass('selectedOrder');
    queryEntity.order = 'a.band.bandName';
    getAllAlbums();
});

$('#rating').click(function () {
    $('.button').removeClass('selectedOrder');
    $('#rating').addClass('selectedOrder');
    queryEntity.order = 'a.currentRating';
    getAllAlbums()
});

function getActualResponseSelectedByYear() {
    let requestCounter = 0;
    return function () {
        requestCounter++;
        let responseCounter = requestCounter;
        queryEntity.year = $('#select_by_release_year').val();
        $.ajax({
            url: '/albums/allAlbums',
            type: 'get',
            data: queryEntity,
            headers: {'X-CSRF-Token': csrfToken},
            success: [function (data, status, response) {
                csrfToken = response.getResponseHeader('X-CSRF-Token');
                if (responseCounter >= requestCounter) {
                    render.renderAlbums(data)
                    requestCounter++;
                }
            }]
        });
    }
}

const actualResponseSelectedByYear = getActualResponseSelectedByYear();

$('#select_by_release_year').keyup(() => {
    actualResponseSelectedByYear();
});

function getActualResponseSelectedByBandName() {
    let requestCounter = 0;
    return function () {
        requestCounter++;
        let responseCounter = requestCounter;
        queryEntity.band = $('#select_by_band_name').val();
        $.ajax({
            url: '/albums/allAlbums',
            type: 'get',
            data: queryEntity,
            headers: {'X-CSRF-Token': csrfToken},
            success: [function (data, status, response) {
                csrfToken = response.getResponseHeader('X-CSRF-Token');
                if (responseCounter >= requestCounter) {
                    render.renderAlbums(data)
                    requestCounter++;
                }
            }]
        })
    };
}

const actualResponseSelectedByBandName = getActualResponseSelectedByBandName();

$('#select_by_band_name').keyup(() => {
    actualResponseSelectedByBandName()
});

$('#openNewAlbumWindow').click(render.openNewAlbumWindow);

$('#closeNewAlbumWindow').click(render.closeNewAlbumWindow);

$('#addAlbum').click(function () {
        let formData = new FormData();
        formData.append('picture', $('#uploadFile')[0].files[0]);
        formData.append('albumName', $('#album_name').val());
        formData.append('releaseYear', $('#release_year').val());
        formData.append('bandName', $('#band_name').val());
        $.ajax({
            url: 'albums/newAlbum',
            type: 'post',
            headers: {'X-CSRF-Token': csrfToken},
            processData: false,
            contentType: false,
            data: formData,
            success: [function (data, status, response) {
                csrfToken = response.getResponseHeader('X-CSRF-Token');
                render.closeNewAlbumWindow();
                getAllAlbums();
            }],
            error: [function (xhr) {
                alert(xhr.responseText);
            }]
        })
    }
);

let albumId = 1;
$(document).on('click', '#openNewTagWindow', function () {
    albumId = +($(this).closest('.albumClone').find('.album_id').html());
    render.openNewTagWindow(albumId);
});

$('#addNewTag').click(function () {
    let tagName = $('#newTagName').val();
    $.ajax({
        url: 'tags/newTag',
        type: 'post',
        headers: {'X-CSRF-Token': csrfToken},
        data: {'tagName': tagName, 'albumId': albumId},
        success: [function (data, status, response) {
            csrfToken = response.getResponseHeader('X-CSRF-Token');
            render.closeNewTagWindow();
            getAllAlbums();
        }],
        error: [errorFunction]
    })
});

$(document).on('click', '.tagSpan', function () {
    let tagName = $(this).text();
    let tagNameStripped = tagName.substring(2, tagName.length - 1);
    $.ajax({
        url: 'albums/addTagToAlbum',
        type: 'post',
        headers: {'X-CSRF-Token': csrfToken},
        data: {'albumId': albumId, 'tagName': tagNameStripped},
        success: [function (data, status, response) {
            csrfToken = response.getResponseHeader('X-CSRF-Token');
            render.closeNewTagWindow();
            getAllAlbums();
        }],
        error: [errorFunction]
    })
});

$(document).on('click', 'label', function () {
    albumId = $(this).closest('.albumClone').find('.album_id').html();
    let rating = $(this).attr('title');
    $.ajax({
        url: 'albums/updateRating',
        type: 'post',
        headers: {'X-CSRF-Token': csrfToken},
        data: {'rating': rating, 'albumId': albumId},
        success: [function (data, status, response) {
            csrfToken = response.getResponseHeader('X-CSRF-Token');
            getAllAlbums();
        }],
        error: [errorFunction]
    })
});

function errorFunction(xhr) {
    alert('Please, authorize');
    $.removeCookie('jwtToken');
    $.removeCookie('userName');
    $('#loginForm').removeClass('hiddenElement');
    $('#logoutForm').addClass('hiddenElement');
    $('#hello').addClass('hiddenElement');
    $('#user').empty();
    location.reload();
}

$('.popup-fade').click(render.popupFadeOut);

$('#closeTagBlock').click(render.closeNewTagWindow);

function authentication() {
    let userName = $('#userName').val();
    let password = $('#password').val();
    $.ajax({
        url: 'auth/login',
        type: 'post',
        headers: {'X-CSRF-Token': csrfToken},
        data: {'userName': userName, 'password': password},
        success: [function (data, status, response) {
            csrfToken = response.getResponseHeader('X-CSRF-Token');
            $.cookie('jwtToken', response.getResponseHeader('jwtToken'), {expires: 1});
            $.cookie('userName', data.userName, {expires: 1});
            if (data.role == 'ADMIN') {
                $('#openNewAlbumWindow').removeClass('hiddenElement');
            }
            $('#hello').removeClass('hiddenElement');
            $('#logoutForm').removeClass('hiddenElement');
            $('#loginForm').addClass('hiddenElement');
            $('#userName').val('');
            $('#password').val('');
            render.getUserName();
        }],
        error: [function (xhr) {
            alert(xhr.status + ' Invalid userName/password combination');
        }]
    })
}

$('#login').click(authentication);

$('#loginForm').on('keypress', function (e) {
    if (e.which == 13) {
        authentication();
    }
})

$('#logout').click(function () {
    $.ajax({
        url: 'auth/logout',
        type: 'post',
        headers: {'X-CSRF-Token': csrfToken},
        success: [function (data, status, response) {
            csrfToken = response.getResponseHeader('X-CSRF-Token');
            $('#openNewAlbumWindow').addClass('hiddenElement');
            $.removeCookie('jwtToken');
            $.removeCookie('userName');
            $('#loginForm').removeClass('hiddenElement');
            $('#logoutForm').addClass('hiddenElement');
            $('#hello').addClass('hiddenElement');
            $('#user').empty();
        }]
    })
});





