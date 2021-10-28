class Render {

    renderAlbums(albumList) {
        if ($.cookie('userName') == 'admin') {
        $('#openNewAlbumWindow').removeClass('hiddenElement');
    }
        $('.albumClone').remove();
        $.each(albumList, function (i, item) {
            let album = $('#albumDiv').clone().removeClass('hiddenElement').addClass('albumClone');
            let rating = item.rating;
            for (let j = 1; j <= rating; j++) {
                album.find('#' + j + '_star').addClass('active');
            }
            album.find('.album_image').attr('src', "http://localhost:8080/images/" + item.pathToPicture);
            album.find('.album_id').text(item.albumId);
            album.find('.album_name').text('Album:  ' + item.albumName);
            album.find('.release_year').text('Year:  ' + item.releaseYear);
            album.find('.band_name').text('Band:  ' + item.bandName);
            let tagList = item.tagList;
            let tags = '';
            for (let j = 0; j < tagList.length -1; j++) {
                if (tagList[j] != undefined) {
                    tags += ' #' + tagList[j] + ',';
                }
            }
            if (tagList[tagList.length - 1] != undefined) {
                tags += ' #' + tagList[tagList.length - 1];
            }
            album.find('.tags').text('Tags:  ' + tags);
            $('.albumsBlock').append(album);
        });
    }

    getUserName() {
        $('#user').text($.cookie('userName'));
    }

    openNewAlbumWindow() {
        $('.newAlbum').removeClass('hiddenElement');
        $('.popup-fade').fadeIn();
        $('#album_name').focus();
    }

    closeNewAlbumWindow() {
        $('#album_name').val('');
        $('#release_year').val('');
        $('#band_name').val('');
        $('#uploadFile').val('');
        $('.popup-fade').fadeOut();
        $('.newAlbum').addClass('hiddenElement');
    }

    openNewTagWindow(albumId) {
        $('.newTag').removeClass('hiddenElement');
        $('.tagPClone').remove();
        $.ajax({
            url: 'tags?albumId=' + albumId,
            type: 'get',
            headers: {'X-CSRF-Token': csrfToken},
            success: [function (data, status, response) {
                csrfToken = response.getResponseHeader('X-CSRF-Token');
                for (let i = 0; i < data.length -1; i++) {
                    let p = $('.tagP').clone().addClass('tagPClone').removeClass('tagP');
                    p.find('.tagSpan').text(' #' + data[i] + ',');
                    $('.tagList').append(p);
                }
                let p = $('.tagP').clone().addClass('tagPClone').removeClass('tagP');
                p.find('.tagSpan').text(' #' + data[data.length -1]);
                $('.tagList').append(p);
            }
            ]
        })
        $('.popup-fade').fadeIn();
        $('#newTagName').focus();
    }

    closeNewTagWindow() {
        $('.popup-fade').fadeOut();
        $('#newTagName').val('');
        $('.newTag').addClass('hiddenElement');
    }

    popupFadeOut(e) {
        if ($(e.target).closest('.popup').length == 0) {
            $(this).fadeOut();
            $('.newTag').addClass('hiddenElement');
            $('.newAlbum').addClass('hiddenElement');
            $('#newTagName').val('');
            $('#album_name').val('');
            $('#release_year').val('');
            $('#band_name').val('');
            $('#uploadFile').val('');
        }
    }

}











