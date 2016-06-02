package com.yikeguan.boardgamestore.test;

import android.test.AndroidTestCase;

import com.yikeguan.boardgamestore.app.App;
import com.yikeguan.boardgamestore.response.LoginBean;
import com.yikeguan.boardgamestore.response.ResultModel;
import com.yikeguan.boardgamestore.resquest.AdvertisementListParams;
import com.yikeguan.boardgamestore.resquest.DeleteCommentGameInfoParams;
import com.yikeguan.boardgamestore.resquest.MessageNoreadNumParams;
import com.yikeguan.boardgamestore.resquest.RegisterThreeParams;
import com.yikeguan.boardgamestore.resquest.SendEmailAllParams;
import com.yikeguan.boardgamestore.resquest.TrendListParams;
import com.yikeguan.boardgamestore.resquest.UpdateMemberInfoParams;
import com.yikeguan.boardgamestore.resquest.game.CollectionGameListParams;
import com.yikeguan.boardgamestore.resquest.game.SearchGameListParams;
import com.yikeguan.boardgamestore.resquest.recommend.RecommendCommentParams;
import com.yikeguan.boardgamestore.util.L;

public class APIServiceTest extends AndroidTestCase {
	private final String TAG = "APIServiceTest";

	// 获取验证码
	public void getCode() throws Exception {
		// 发送短信验证码
		// TODO ResultModel mc = new SendSMSParams(getContext(), "18610041095")
		// .getResult();
		// 发送邮箱验证码
		// ResultModel mc = new SendMailParams(getContext(), "693623533@qq.com")
		// .getResult();
		// L.i(TAG, "sendSMS Code=" + mc.getCode());

		App.app.preferences.save("456", 2);
	}

	// 注册
	public void register() throws Exception {
		// 手机号码注册
		// TODO ResultModel mc = new RegisterPhoneParams(getContext(),
		// "18610041095",
		// "nc123456", "593228", null, null, null, null, null).getResult();
		// 邮箱注册
		// ResultModel mc = new RegisterMailParams(getContext(),
		// "693623533@qq.com", "nc123456", "593228", null, null, null,
		// null, null).getResult();
		// TODO 第三方注册 1微信2QQ 3新浪 0 其它
		ResultModel mc = new RegisterThreeParams(getContext(), "2088063743",
				"1", "老牛往前冲",
				"http://tp4.sinaimg.cn/2088063743/180/5598356279/1", null, "男",
				"期待中...").getResult();
		LoginBean bean = (LoginBean) mc.getResult();
		L.i(TAG, "registerPhone Session_key=" + bean.getSession_key());
	}

	// 登录
	public void login() throws Exception {
		// 手机号登录
		// ResultModel mc = new LoginPhoneParams(getContext(), "13810116246",
		// "zhaizhanpo").getResult();
		// 邮箱登录
		// ResultModel mc = new LoginMailParams(getContext(),
		// "zhaizhanpo@126.com", "zhaizhanpo").getResult();
		// 用户名
		// ResultModel mc = new LoginLoadNameParams(getContext(),
		// "niucong",
		// "niucong").getResult();
		// 第三方登录
		// TODO ResultModel mc = new LoginThreeParams(getContext(), "123456")
		// .getResult();
		// LoginBean bean = (LoginBean) mc.getResult();
		// L.i(TAG, "login Session_key=" + bean.getSession_key());
	}

	// 社员
	public void getMember() throws Exception {
		// 获取用户基本信息
		// ResultModel mc = new GetMemberBasicParams(getContext(), null)
		// .getResult();
		// 获取用户详细信息
		// ResultModel mc = new GetMemberInfoParams(getContext(),
		// "1").getResult();
		// LoginBean bean = (LoginBean) mc.getResult();
		// L.i(TAG, "login Session_key=" + bean.getSession_key());
		// 获取用户列表
		// ResultModel mc = new FindMemberListParams(getContext(), "0", "10",
		// null)
		// .getResult();

		// TODO 修改用户密码
		// ResultModel mc = new UpdateMemberPasswordParams(getContext(),
		// "niucong", "693623533").getResult();
		// TODO 修改头像信息
		// ResultModel mc = new
		// UploadMemberHeadParams(getContext()).getResult();
		// TODO 修改用户基本信息
		ResultModel mc = new UpdateMemberInfoParams(getContext(), "niucong",
				"2", "Hello").getResult();
		// L.i(TAG, "sendSMS Code=" + mc.getCode());
	}

	// 游戏
	public void getGame() throws Exception {
		// 获取游戏基本信息
		// ResultModel mc = new GetGameBasicParams(getContext(),
		// "1").getResult();
		// 获取游戏详细信息
		// ResultModel mc = new GetGameInfoParams(getContext(),
		// "1").getResult();
		// 获取游戏列表
		// ResultModel mc = new FindGameListParams(getContext(), "0", "10",
		// null)
		// .getResult();
		// 搜索游戏列表-模糊搜索
		ResultModel mc = new SearchGameListParams(getContext(), "0", "10",
				"Modern Art", "", "").getResult();
		// 搜索游戏列表-设计师
		// ResultModel mc = new SearchGameDesignerListParams(getContext(), "",
		// "0", "10").getResult();
		// 搜索游戏列表-出版社
		// ResultModel mc = new SearchGamePublisherListParams(getContext(), "",
		// "0", "10").getResult();
		// 出版社列表
		// PublisherListWithStart:60andLimit:8
		// ResultModel mc = new GamePublisherListParams(getContext(), "", "60",
		// "8").getResult();
		// 设计师列表
		// DesignerListWithStart:295 andLimit:8
		// ResultModel mc = new GameDesignerListParams(getContext(), "", "0",
		// "10")
		// .getResult();
	}

	// 关注、访问、收藏记录
	public void flow() throws Exception {
		ResultModel mc = new CollectionGameListParams(getContext(), "1", "10",
				"").getResult();
		// 关注、取消关注游戏
		// ResultModel mc = new FlowGameParams(getContext(), "1", true)
		// .getResult();
		// 关注、取消关注人
		// ResultModel mc = new FlowMemberParams(getContext(), "4", true)
		// .getResult();
		// 某款游戏关注、访问、收藏的人群
		// ResultModel mc = new FlowGameMembersListParams(getContext(), "0",
		// "10",
		// "1", 2).getResult();
		// 某个人关注的游戏
		// ResultModel mc = new FlowMemberGamesListParams(getContext(), "0",
		// "10",
		// "").getResult();
		// TODO 0:某个人关注的人群、1:某个人被关注的人群、2：某个人访问的人群、3：某个人被访问的人群（最近谁来过）
		// ResultModel mc = new FlowMembersListParams(getContext(), "0", "10",
		// "1", 3).getResult();
		// 收藏、取消收藏游戏
		// ResultModel mc = new CollectionGameParams(getContext(), "1", true)
		// .getResult();
	}

	// 评论OK
	public void commentGame() throws Exception {
		// 评论游戏/@某人
		// ResultModel mc = new CommentGameParams(getContext(), "1", "hello")
		// .getResult();
		// 删除游戏的评论
		ResultModel mc = new DeleteCommentGameInfoParams(getContext(), "2")
				.getResult();
		// 获取某款游戏的评论列表
		// ResultModel mc = new CommentGameListParams(getContext(), "0", "10",
		// "1")
		// .getResult();
	}

	// TODO 动态相关接口
	public void trend() throws Exception {
		// 获取所有人的公开动态
		ResultModel mc = new TrendListParams(getContext(), "0", "10", "")
				.getResult();
		// 获取朋友圈动态
		// ResultModel mc = new TrendFriendsListParams(getContext(), "0", "10")
		// .getResult();
		// 获取某个人的动态
		// ResultModel mc = new TrendMemberListParams(getContext(), "", "0",
		// "10",
		// "").getResult();
		// 删除某条我发表的动态
		// ResultModel mc = new DeleteTrendParams(getContext(), "").getResult();
		L.d(TAG, "" + mc.getCode());
	}

	// TODO 通知相关接口
	public void notice() throws Exception {
		// // 获取通知列表
		// ResultModel mc = new NoticeListParams(getContext(), "0", "10")
		// .getResult();

		// 获取未读通知列表
		// ResultModel mc = new NoticeNoreadListParams(getContext(), "0", "10")
		// .getResult();

		// 获取未读通知个数
		// ResultModel mc = new NoticeNoreadNumParams(getContext()).getResult();

		// 删除某条通知
		// ResultModel mc = new DeleteNoticeParams(getContext(),
		// "").getResult();

		// 修改通知状态
		// ResultModel mc = new UpdateNoticeParams(getContext()).getResult();

		// 群发通知
		// ResultModel mc = new SendNoticeAllParams(getContext(), "群发通知测试")
		// .getResult();

		// 群发邮件
		ResultModel mc = new SendEmailAllParams(getContext(), "群发邮件测试")
				.getResult();
	}

	// TODO 私信相关接口
	public void message() throws Exception {
		// 发送私信
		// ResultModel mc = new SendMessageParams(getContext(), "").getResult();
		// 删除单条私信
		// ResultModel mc = new DeleteMessageParams(getContext(),
		// "").getResult();
		// 删除某个联系人（所有私信）
		// ResultModel mc = new DeleteMemberMessageParams(getContext(), "")
		// .getResult();
		// 获取联系人列表
		// ResultModel mc = new ContactListParams(getContext(), "0", "10")
		// .getResult();
		// 获取我和某人的私信列表
		// ResultModel mc = new MessageListParams(getContext(), "0", "10")
		// .getResult();
		// 获取私信未读数量
		ResultModel mc = new MessageNoreadNumParams(getContext()).getResult();

	}

	// TODO 广告相关接口
	public void advertisement() throws Exception {
		// 获取广告列表
		ResultModel mc = new AdvertisementListParams(getContext(), "")
				.getResult();
	}

	// 晒图
	public void shaitu() throws Exception {
		// 发布晒图
		// ResultModel mc = new SunPicCreateParams(getContext(), "123", "abc",
		// null,
		// null).getResult();

		// // 获取单个晒图详情
		// ResultModel mc = new SunPicInfoParams(getContext(),
		// "56").getResult();

		// // 评论晒图
		// ResultModel mc = new SunPicCommentParams(getContext(), "56", "评论一下")
		// .getResult();
		ResultModel mc = new RecommendCommentParams(getContext(), "94", "评论一下")
				.getResult();

		// // 删除评论
		// ResultModel mc = new SunPicDeleteCommentParams(getContext(), "220")
		// .getResult();

		// // 获取评论列表
		// ResultModel mc = new SunPicCommentListParams(getContext(), "56")
		// .getResult();

		// 获取晒图列表/获取某人晒图列表
		// ResultModel mc = new SunPicFindListParams(getContext(),
		// "", "0", "2").getResult();
		// ResultModel mc = new RecommendFindListParams(getContext(), "", "0",
		// "2")
		// .getResult();
	}

}
