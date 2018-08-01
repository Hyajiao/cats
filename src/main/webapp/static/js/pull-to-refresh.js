var myScroll,
    pullUpEl, pullUpOffset,
    page = 1,
    pullUp = true;

function pullUpAction () {
    page++;
    setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
        var el, li, i;
        el = document.getElementById('thelist');
        loaddata(page,function(){
            myScroll.refresh();
        });
        // myScroll.refresh();		// Remember to refresh when contents are loaded (ie: on ajax completion)
    }, 500);	// <-- Simulate network congestion, remove setTimeout from production!
}

function loaded() {
    pullUpEl = document.getElementById('pullUp');
    pullUpOffset = pullUpEl.offsetHeight;

    myScroll = new iScroll('wrapper', {
        useTransition: true,
        onRefresh: function () {
            if (pullUpEl.className.match('loading')) {
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多';
                pullUpEl.style.display = "none";
            }
        },
        onScrollMove: function () {
            console.log("this.y="+this.y);
            console.log("this.maxScrollY="+this.maxScrollY);
            if(this.y > 0){
                pullUp = false;
            }else{
                pullUpEl.style.opacity = 1;
                pullUpEl.style.display = "block";
                pullUp = true;
            }

            if (this.y < 0 && this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
                pullUpEl.className = 'flip';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '释放刷新';
                this.maxScrollY = this.maxScrollY;
            } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多';
                this.maxScrollY = pullUpOffset;
            }
        },
        onScrollEnd: function () {
            if (pullUp && pullUpEl.className.match('flip')) {
                pullUpEl.className = 'loading';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中';
                pullUpAction();	// Execute custom function (ajax call?)
            }
        }
    });

    setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}



document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

document.addEventListener('DOMContentLoaded', function () {
    loaddata(1);
    setTimeout(loaded, 200);
 }, false);

function search() {
    $("#thelist").html("");
    page = 1;
    loaddata(page,function(){
        $(".cover").hide();
        //隐藏搜索框
        $('.pop_input').hide();
        $('.expertPalace_tag').show();
        //隐藏键盘
        document.activeElement.blur();
        //刷新myscroll 否则会导致检索出的数据在可视范围之外
        myScroll.refresh();
    })
}

