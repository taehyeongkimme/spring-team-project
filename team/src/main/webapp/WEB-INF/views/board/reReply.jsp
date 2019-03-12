<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
	textarea {
		resize: none;
		min-height: 1rem;
		outline: none; 
		width: 100%;
		border: none;
		border-bottom: 1px solid #000;
		overflow: hidden;
		transform: scaleX(1);
	}
	
	.rrbtns {
		flex: auto;
	}
</style>
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }" />
<script>
	var currentPage = 1;
	
	var bnum = ${boardDTO.bnum};  //원글 번호
	var rereqPage = 1; //요청페이지 1

	var l_id = "${user.id}"; // 로긴 아이디
	var l_nickname = "${user.nickname}"; //로긴 닉네임
  
    // 대댓글 작성 html코드 시작-----------------------------------------------
    var reply_str = '';
    reply_str += "<li data-rrnum='' data-form='write' class='list-group-item m-0 p-0'>";
    reply_str += "  <div>";
    reply_str += "    <div class='row m-0 p-0'>";
    reply_str += "      <div class='col-1'></div>";
    reply_str += "      <div class='col-11'>";
    reply_str += "        <textarea class='autosize'></textarea></div>";
    reply_str += "      <div class='col-12 text-right'>";
    reply_str += "        <a href='javascript:void(0)' class='rrclose badge badge-light mx-0 px-2'>취소</a>";
    reply_str += "        <a href='javascript:void(0)' class='rrreply badge badge-light mx-0 px-2'>등록</a>";
    reply_str += "      </div>";
    reply_str += "    </div>";
    reply_str += "  </div>";
    reply_str += "</li>";
    //대댓글 작성 html코드 끝-----------------------------------------------

    // 대댓글 수정 html코드 시작-----------------------------------------------
    var modify_str = '';
    modify_str += "<li data-rrnum='' data-form='modify' class='list-group-item m-0 p-0'>";
    modify_str += "  <div>";
    modify_str += "    <div class='row m-0 p-0'>";
    modify_str += "      <div class='col-1'></div>";
    modify_str += "      <div class='col-11'>";
    modify_str += "        <textarea class='autosize'></textarea></div>";
    modify_str += "      <div class='col-12 text-right'>";
    modify_str += "        <a href='javascript:void(0)' class='rrclose badge badge-light mx-0 px-2'>취소</a>";
    modify_str += "        <a href='javascript:void(0)' class='rrmodify badge badge-light mx-0 px-2'>수정</a>";
    modify_str += "      </div>";
    modify_str += "    </div>";
    modify_str += "  </div>";
    modify_str += "</li>";
    //대댓글 수정 html코드 끝-----------------------------------------------
    $(function(){
    	
    	//댓글목록 보이기
		replyList(rereqPage);

		//댓글 글자수 제한 시작-------------------------------------
		var $rcontent = $("#rcontent");
		$rcontent.on("keyup",function(){
			var rcontent = $("#rcontent").val(); 		//댓글본문
			var cnt = rcontent.length;

			$("#count").html(cnt + '/100');

			if(cnt > 100){

				$rcontent.addClass("is-invalid");
				//100자초과시 잘라내기
				rcontent = rcontent.substring(rcontent.length-100);
				$rcontent.val(rcontent);

				$("#count").html(rcontent.length + ' / 100');
				return false;
			}
			$rcontent.removeClass("is-invalid");
		});
		$rcontent.keyup();
		//댓글 글자수 제한 끝---------------------------------------

		// 댓글 작성
		$("#reply-submit-button").on("click",function() {
			var rid = $("#rid").text();  				 		//작성자 id
			var rnickname = $("#rnickname").text(); //작성자 별칭
			var rcontent = $("#rcontent").val(); 		//댓글본문
			var cnt = rcontent.trim().length;
			//console.log(rid);
			//console.log(rcontent);
			if(cnt == 0){
				$("#rcontent").attr("placeholder","댓글입력바랍니다.");
				$("#rcontent").focus();
				return false;
			}

			//AJAX 비동기 처리기술
			$.ajax({
				type : "POST",  // post, get, put, delete
				url  : "/rboard/posts",
				headers:{
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : "text",
				data:JSON.stringify({
					bnum: bnum,
					rid: rid,
					rnickname: rnickname,
					rcontent: rcontent
				}),

				success:function(result){
					//댓글목록 호출
					//console.log(result);
					replyList(currentPage);
					
					$("#rcontent").val('');
				},
				error :function(xhr, status, err){
					console.log("xhr:"+xhr);
					console.log("status:"+status);
					console.log("err:"+err);
				}
			});
		});

	});

	//요청페이지에 대한 댓글목록 가져오기
	function replyList(rereqPage, mode){

	var str="";
    var rrnickname ="";  // 부모댓글의 별칭
    var rrid = "";       // 부모댓글의 아이디

		$.ajax({
			type:"GET",
			url:"/rboard/posts/map/"+bnum+"/"+rereqPage,
			dataType: "JSON",
			success:function(data,status,xhr){
				console.log(rereqPage);
				
				
				if (data.pagecriteria.endPage != rereqPage && mode != 'user'){
					replyList(data.pagecriteria.endPage > 1 ? data.pagecriteria.endPage : 1 , 'user');
				}
				str += "<ul class='list-group list-group-flush'>";
				
				$.each(data.item,function(idx, rec){
					if(rec.rbdto !=null){
						rrnickname = rec.rbdto.rnickname; // 부모댓글의 별칭
						rrid       = rec.rbdto.rrid;      // 부모댓글의 아이디
					}

					//삭제유무 판단
					if(rec.isdel == 'Y'){
						//들여쓰기
						if( rec.rindent == 0){
						// 1step
							str += "<li data-rnum='"+rec.rnum+"' class='reList list-group-item m-0 p-0'>";
							str += "  <div>";
							str += "    <div class='row m-3 p-0'>";
							str += "      <div class='col-1'>";
							str += "      </div>";
							str += "      <div class='col-11'>";
							str += "        <p class='mb-0'><span class='mr-1 text-warning'>삭제된 게시물입니다.</span></p>";
							str += "      </div>";
							str += "    </div>";
						}else{
							str += "<li data-rnum='"+rec.rnum+"' data-rrnum='"+rec.rrnum+"' class='reList list-group-item m-0 p-0'>";
							str += "  <div>";
							str += "    <div class='row m-3 p-0'>";
							str += "      <div class='col-1'></div>";
							str += "      <div class='col-1'>";
							str += "      </div>";
							str += "      <div class='col-10'>";
							str += "        <p class='mb-0'><span class='mr-1 text-warning'>삭제된 게시물입니다.</span></p>";
							str += "      </div>";
							str += "    </div>";
						}
						str += "  </div>";
						str += "</li>";

						//console.log(str);
						//}
					}else{

						//들여쓰기
						if( rec.rindent == 0){
						// 1step
							str += "<li data-rnum='"+rec.rnum+"' class='reList list-group-item m-0 p-0'>";
							str += "  <div>";
							str += "    <div class='row m-0 p-0'>";
							str += "      <div class='col-1'>";
							str += "      </div>";
							str += "      <div class='col-11'>";
							str += "        <h6>"+rec.rnickname+"("+rec.rid+") <small class='ml-2'><i>"+rec.rcdate+"</i></small></h6>";
							str += "        <p class='replybody'>"+rec.rcontent+"</p>";
							str += "      </div>";
							str += "    </div>";
							str += "    <div class='row m-0 p-0'>";
						}else{
							str += "<li data-rnum='"+rec.rnum+"' data-rrnum='"+rec.rrnum+"' class='reList list-group-item m-0 p-0'>";
							str += "  <div>";
							str += "    <div class='row m-0 p-0'>";
							str += "      <div class='col-1'></div>";
							str += "      <div class='col-1'>";
							str += "      </div>";
							str += "      <div class='col-10'>";
							str += "        <h6>"+rec.rnickname+"("+rec.rid+") <small class='ml-2'><i>"+rec.rcdate+"</i></small></h6>";
							str += "        <p class='replybody'>"+rec.rcontent+"</p>";
							str += "      </div>";
							str += "    </div>";
							str += "    <div class='row m-0 p-0'>";
							str += "      <div class='col-1'></div>";
						}
						str += "      <div class='col-1'></div>";
						str += "      <div class='col-9 pl-4'>";
						str += "        <a href='javascript:void(0)' data-active='off' ";
						str += "        class='reReplyBtn badge badge-light px-2'>댓글</a>";
						str += "      </div>";

						if(l_id == rec.rid){
						str += "      <div class='rrbtns m-0 p-0 text-right'>";
						str += "        <a href='javascript:void(0)' data-active='off' ";
						str += "        class='reModifyBtn badge badge-light mx-0 px-2'>수정</a>";
						str += "        <a href='javascript:void(0)' data-active='off' ";
						str += "        class='reDelBtn badge badge-light mx-0 px-2'>삭제</a>";
						str += "      </div>";
						}

						str += "    </div>";
						str += "  </div>";
						str += "</li>";

						//console.log(str);
						}
				});
				str += "</ul>";

				//댓글 목록 삽입
				$("#replyList").html(str);

				// 페이지 리스트호출
				showPageList(data.pagecriteria);

				// 댓글목록 이벤트처리
				doActionEvent();
				
				
			},
			error :function(xhr, status, err){
				console.log("xhr:"+xhr);
				console.log("status:"+status);
				console.log("err:"+err);
			}

		});

		//페이지번호 클릭시 이벤트 처리
		$("#pageNumList").on("click", "li a", function(e){
			e.preventDefault();  					// 현재 이벤트의 기본 동작을 중단.
			e.stopImmediatePropagation(); // 현재 이벤트가 상위 및 현재 레벨에 걸린 다른 이벤트도 동작않도록 중단.
			//e.stopPropagation();        // 현재 이벤트가 상위로 전파되지 않도록 중단.
			rereqPage = $(this).attr("href");
			replyList(rereqPage, 'user');
			currentPage = rereqPage;
		});
	}

	// 페이징구현 
	function showPageList(pagecriteria){
		//console.log(pagecriteria);
		
		var str="";
		
		//이전페이지여부
		if(pagecriteria.prev){		
		 //처음	
		 str += "<li class='page-item'>";
	   str += "<a class='page-link' href='1' tabindex='-1' aria-disabled='true'>◀</a></li>";
	   
	   //이전페이지
	   str += "<li class='page-item'><a class='page-link href='";
	   str +=  (pagecriteria.startPage-1);
	   str += "' tabindex='-1' aria-disabled='true'>◁</a></li>";
		}
		
		//페이지 1~10
		for( i=pagecriteria.startPage, end=pagecriteria.endPage; i<=end; i++){
		
	    //<!-- 현재페이지와 요청페이지가 다르면 -->
	   	if(pagecriteria.recordCriteria.reqPage != i) {           
	   		str += "<li class='page-item'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
	   	}else{
	    	//<!-- 현재페이지와 요청페이지가 같으면 -->
	    	str += "<li class='page-item active'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
	   	}
		}

		//다음페이지여부
		if(pagecriteria.next){
		//다음페이지
	    str += "<li class='page-item'><a class='page-link' href='";
	    str += (pagecriteria.endPage+1);
	    str += "'>▷</a></li>";

		//마지막
	    str += "<li class='page-item'><a class='page-link' href='";
	    str += (pagecriteria.finalEndPage);
	    str += "'>▷</a></li>";
		}

		//페이징 삽입
		$("#pageNumList").html(str);

	}

	//댓글목록 이벤트처리
	function doActionEvent(){
    	

      //대댓글 작성 클릭시
      $('.reReplyBtn').on('click',function(e){
        var $liEle = $(this).parents('li');
        var $data_rnum  =  $liEle.attr('data-rnum'); //댓글번호
        var $data_rrnum =  $liEle.next().attr('data-rrnum'); //대댓글번호
        var $data_form   = $liEle.next().attr('data-form'); //

        //대댓글 양식이 없을경우만 추가
        if( $data_rnum != $data_rrnum) {
          $liEle.after(reply_str);
          
          //현재글의 대댓글은 있으나 댓글작성 중이 아닐경우
        }else if($data_rnum == $data_rrnum && $data_form == null){
          $liEle.after(reply_str);

        }else if($data_rnum == $data_rrnum && $data_form == 'write') {

          var $textarea = $liEle.next().find('textarea');
          var $tmp = $textarea.val();

          $liEle.next().replaceWith(reply_str);
          $liEle.next().find('textarea').val($tmp);

        }else if($data_rnum == $data_rrnum && $data_form == 'modify'){

          $liEle.next().replaceWith(reply_str);
          $liEle.next().find('textarea').val($tmp);
        }

        $liEle.next().attr('data-rrnum', $liEle.attr('data-rnum'));

        //대댓글 양식 높이 자동 조절
        var $textareaEle = $('textarea.autosize');
        $textareaEle.on('keyup focus', function(){
          $(this)[0].style.height = 'auto';
          $(this).css('height', $(this).prop('scrollHeight'));
          console.log($(this).prop('scrollHeight'));
        });
        $textareaEle.trigger('focus');

        //대댓글 양식 닫기
        $('.rrclose').on('click',function(e){
          e.stopImmediatePropagation();
          $liEle.next().remove();
        });
        
   		//대댓글 등록 - bnum,rrnum,rid,rnickname,rcontent
   		$(".rrreply").on("click",function(){
   			console.log('리댓글 등록');	
   			var $li = $(this).parents("li");
   			var rrnum = $li.attr('data-rrnum');
   			var rcontent = $li.find('textarea').val();
   			//console.log("rcontent="+rcontent);
   			
   			$.ajax({
   				type:"POST",
   				url:"/rboard/rposts",
   			//	contentType:"application/json",
     			headers:{
   					"Content-Type" : "application/json",
   					"X-HTTP-Method-Override" : "POST"
   				},
   				dataType:"text",
   				data:JSON.stringify({
						bnum : bnum,
   					rrnum : rrnum,
   					rid : l_id,
   					rnickname : l_nickname,
   					rcontent : rcontent
   				}),
        	success:function(result){
        		replyList(currentPage, 'user');
        	},
  				error:function(xhr, status, err){
  					console.log("xhr:"+xhr);
  					console.log("status:"+status);
  					console.log("err:"+err);
  				}   				
   			});
   		});

      });

      //대댓글 수정 클릭시
      $('.reModifyBtn').on('click',function(e){
        //console.log(e.target.innerText);

        var $liEle = $(this).parents('li');
        var $data_rnum  =  $liEle.attr('data-rnum'); //댓글번호
        var $data_rrnum =  $liEle.next().attr('data-rrnum'); //대댓글번호
        var $data_form   = $liEle.next().attr('data-form'); //

        //대댓글 수정 양식이 없을경우만 추가
        if( $data_rnum != $data_rrnum) {
          var tmp = $liEle.find('p').text();
          $liEle.after(modify_str);
          $liEle.next().find('textarea').val(tmp);

        }else if($data_rnum == $data_rrnum && $data_form == 'write') {
          $tmp = $liEle.find('p').text();
          $liEle.next().replaceWith(modify_str);
          $liEle.next().find('textarea').val($tmp);

        }else if($data_rnum == $data_rrnum && $data_form == 'modify') {

          var $textarea = $liEle.next().find('textarea')
          var $tmp = $textarea.val();
          if($tmp.trim().length == 0 ){
            $tmp = $liEle.find('p').text();
          }

          $liEle.next().replaceWith(modify_str);
          $liEle.next().find('textarea').val($tmp);
        }
        $liEle.next().attr('data-rrnum', $liEle.attr('data-rnum'));

        //대댓글 수정 양식 높이 자동 조절
        var $textareaEle = $('textarea.autosize');
        try{
        $textareaEle.trigger();
        }catch(e){
        	console.log(e);
        }
        $textareaEle.on('keyup focus', function(){
          $(this)[0].style.height = 'auto';
          $(this).css('height', $(this).prop('scrollHeight'));
        });
        $textareaEle.trigger('focus');

        //대댓글 수정 양식 닫기
        $('.rrclose').on('click',function(e){
          e.stopImmediatePropagation();
          $liEle.next().remove();
        });
        
        
  		//수정 rnum,rcontent
   		$(".rrmodify").on("click",function(){
   			var $li = $(this).parents("li");
   			var rnum = $li.attr("data-rrnum");
   			var rcontent = $li.find('textarea').val();   			
   			$.ajax({
   				type: "PUT",
   				url: "/rboard/posts",
     			//	contentType:"application/json",
     			headers:{
   					"Content-Type" : "application/json",
   					"X-HTTP-Method-Override" : "POST"
   				},
   				dataType:"text",
   				data:JSON.stringify({
						rnum : rnum,
   					rcontent : rcontent
   				}),
        	success:function(result){
        		replyList(/*rereqPage*/currentPage, 'user');
        	},
  				error:function(xhr, status, err){
  					console.log("xhr:"+xhr);
  					console.log("status:"+status);
  					console.log("err:"+err);
  				}   				   				
   			});
   			
   		});   

      });

      //댓글 삭제 클릭시
      $('.reDelBtn').on('click', function(){
    	  var $li = $(this).parents('li');
          var rnum = $li.attr('data-rnum');
          //console.log("rnum="+rnum);
          
          $.ajax({
          	type:"DELETE",
          	url: "/rboard/posts/"+rnum,
    /*       	headers:{
          		"Content-Type" :"application/json", //전송내용이 json포맷
          		"X-HTTP-Method-Override" :"POST" // 낮은버전브라우져에서 POST방식으로 강제변경. 
          	}, */
          	dataType:"text",
          	success:function(){
          		replyList(currentPage, 'user');
          	},
    				error:function(xhr, status, err){
    					console.log("xhr:"+xhr);
    					console.log("status:"+status);
    					console.log("err:"+err);
    				}
        	});
        $('#t-del-msg').toast('show');
      });

      //tootip적용
      $("[data-toggle='tooltip']").tooltip();
    }

  </script>

<style>
.reply-box-form {
	padding: 10px;
	border-top: 2px solid red;
	border-bottom: 2px solid red;
	background: #fafafa;
	height: 200px;
}

.reply-input-form {
	margin: 0px;
	width: 100%;
	height: 170px;
}

.reply-input-part {
	height: 140px;
}

.reply-button-part {
	width: 100%;
	height: 35px;
}

.reply-input-area {
	resize: none;
	padding: 10px;
	margin: 0px;
	width: 100%;
	height: 78px;
}

.submit-button-group {
	padding-top: 5px;
	float: right;
}

#reply-submit-button, #reply-cancel-button {
	padding: 0px;
	margin: 0px;
	width: 50px;
	height: 30px;
}

#count {
	margin: 0px;
	width: 100%;
	height: 25px;
}
</style>

<div class="container">

<sec:authorize var="admin" access="hasRole('ROLE_ADMIN')" />
	<!-- 댓글 목록 -->
	<div id="replyList"></div>
	<!-- 댓글 등록 -->
	<div class="reply-box-form">
		<div class="reply-input-form">
			<div>
				<span id="rnickname">${user.nickname}</span>/(<span id="rid">${user.id}</span>)
			</div>
			<div class="reply-input-part">
				<textarea class="reply-input-area" id="rcontent"></textarea>
				<p class="text-right m-0" id="count"></p>
				<div class="reply-button-part">
					<div class="submit-button-group">
						<button type="button" id="reply-submit-button"
							class="btn btn-outline-secondary btn-sm">등록</button>
						<button type="button" id="reply-cancel-button"
							class="btn btn-outline-secondary btn-sm">취소</button>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 댓글 페이징 -->
	<div>
		<nav aria-label="Page navigation example">
			<ul id="pageNumList"
				class="pagination pagination-sm justify-content-center">
			</ul>
		</nav>
	</div>
	<!-- tosts -->
	<div role='alert' aria-live='assertive' aria-atomic='true'
		class='toast' id='t-del-msg' data-autohide='true' data-delay='1300'
		style='position: absolute; right: 50px; bottom: 50px;'>
		<div class='toast-header'>
			<!--img src='...' class='rounded mr-2' alt='...'-->
			<i class='far fa-trash-alt fa-lg mr-2' style='color: #f00;'></i> <strong
				class='mr-auto'>[삭제]</strong> <small class='invisible'>11
				mins ago</small>
			<button type='button' class='ml-2 mb-1 close' data-dismiss='toast'
				aria-label='Close'>
				<span aria-hidden='true'>&times;</span>
			</button>
		</div>
		<div class='toast-body'>댓글이 삭제되었습니다.</div>
	</div>
</div>

