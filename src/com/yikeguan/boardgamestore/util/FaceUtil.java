package com.yikeguan.boardgamestore.util;

import com.yikeguan.boardgamestore.R;

/**
 * 表情
 */
public class FaceUtil {

	public final static String TEXT_BACK = "[BACK]";
	public final static String TEXT_NULL = "[NULL]";
	public final static int RES_BACK = R.drawable.face_delete;
	public final static int RES_NULL = R.drawable.nothing;

	public final static int[] FACE_RES_DIDS = new int[] {
			R.drawable.d_madaochenggong, R.drawable.d_chitangyuan,
			R.drawable.d_duixiang, R.drawable.o_fahongbao,
			R.drawable.d_zhajipijiu, R.drawable.d_travel, R.drawable.d_zuiyou,
			R.drawable.d_hehe, R.drawable.d_xixi, R.drawable.d_haha,
			R.drawable.d_aini, R.drawable.d_wabishi, R.drawable.d_chijing,
			R.drawable.d_yun, R.drawable.d_lei, R.drawable.d_chanzui,
			R.drawable.d_zhuakuang, R.drawable.d_heng, R.drawable.d_keai,
			R.drawable.d_nu, R.drawable.d_han, R.drawable.d_haixiu,
			R.drawable.d_shuijiao, R.drawable.d_qian, R.drawable.d_touxiao,
			R.drawable.d_ku, R.drawable.d_shuai, R.drawable.d_bizui,
			R.drawable.d_bishi, R.drawable.d_huaxin, R.drawable.d_guzhang,
			R.drawable.d_beishang, R.drawable.d_sikao, R.drawable.d_shengbing,
			R.drawable.d_qinqin, R.drawable.d_numa, R.drawable.d_taikaixin,
			R.drawable.d_landelini, R.drawable.d_youhengheng,
			R.drawable.d_zuohengheng, R.drawable.d_xu, R.drawable.d_weiqu,
			R.drawable.d_tu, R.drawable.d_kelian, R.drawable.d_dahaqi,
			R.drawable.d_jiyan, R.drawable.d_shiwang, R.drawable.d_ding,
			R.drawable.d_yiwen, R.drawable.d_kun, R.drawable.d_ganmao,
			R.drawable.d_baibai, R.drawable.d_heixian, R.drawable.d_yinxian,
			R.drawable.f_hufen, R.drawable.l_xin, R.drawable.l_shangxin,
			R.drawable.d_zhutou, R.drawable.d_xiongmao, R.drawable.d_tuzi,
			R.drawable.h_woshou, R.drawable.h_ye, R.drawable.h_good,
			R.drawable.h_ruo, R.drawable.h_buyao, R.drawable.h_ok,
			R.drawable.h_haha, R.drawable.h_lai, R.drawable.f_v5,
			R.drawable.w_xianhua, R.drawable.o_zhong, R.drawable.w_fuyun,
			R.drawable.o_feiji, R.drawable.w_yueliang, R.drawable.w_taiyang,
			R.drawable.w_weifeng, R.drawable.w_shachenbao, R.drawable.w_xiayu,
			R.drawable.f_geili, R.drawable.f_shenma, R.drawable.o_weiguan,
			R.drawable.o_huatong, R.drawable.d_aoteman, R.drawable.d_shenshou,
			R.drawable.f_meng, R.drawable.f_jiong, R.drawable.f_zhi,
			R.drawable.o_liwu, R.drawable.f_xi, R.drawable.o_weibo,
			R.drawable.o_yinyue, R.drawable.o_lvsidai, R.drawable.o_dangao,
			R.drawable.o_lazhu, R.drawable.o_ganbei, R.drawable.d_nanhaier,
			R.drawable.d_nvhaier, R.drawable.d_feizao, R.drawable.o_zhaoxiangji };

	public final static String[] FACE_DTEXTS = new String[] { "[马到成功]",
			"[吃元宵]", "[马上有对象]", "[发红包]", "[炸鸡和啤酒]", "[带着微博去旅行]", "[最右]",
			"[呵呵]", "[嘻嘻]", "[哈哈]", "[爱你]", "[挖鼻屎]", "[吃惊]", "[晕]", "[泪]",
			"[馋嘴]", "[抓狂]", "[哼]", "[可爱]", "[怒]", "[汗]", "[害羞]", "[睡觉]", "[钱]",
			"[偷笑]", "[酷]", "[衰]", "[闭嘴]", "[鄙视]", "[花心]", "[鼓掌]", "[悲伤]",
			"[思考]", "[生病]", "[亲亲]", "[怒骂]", "[太开心]", "[懒得理你]", "[右哼哼]",
			"[左哼哼]", "[嘘]", "[委屈]", "[吐]", "[可怜]", "[打哈气]", "[挤眼]", "[失望]",
			"[顶]", "[疑问]", "[困]", "[感冒]", "[拜拜]", "[黑线]", "[阴险]", "[互粉]",
			"[心]", "[伤心]", "[猪头]", "[熊猫]", "[兔子]", "[握手]", "[耶]", "[good]",
			"[弱]", "[不要]", "[ok]", "[haha]", "[来]", "[威武]", "[鲜花]", "[钟]",
			"[浮云]", "[飞机]", "[月亮]", "[太阳]", "[微风]", "[沙尘暴]", "[下雨]", "[给力]",
			"[神马]", "[围观]", "[话筒]", "[奥特曼]", "[草泥马]", "[萌]", "[囧]", "[织]",
			"[礼物]", "[喜]", "[围脖]", "[音乐]", "[绿丝带]", "[蛋糕]", "[蜡烛]", "[干杯]",
			"[男孩儿]", "[女孩儿]", "[肥皂]", "[照相机]" };

	public final static int[] FACE_RES_LIDS = new int[] {
			R.drawable.lxh_xiaohaha, R.drawable.lxh_haoaio, R.drawable.lxh_oye,
			R.drawable.lxh_toule, R.drawable.lxh_leiliumanmian,
			R.drawable.lxh_juhan, R.drawable.lxh_koubishi,
			R.drawable.lxh_qiuguanzhu, R.drawable.lxh_haoxihuan,
			R.drawable.lxh_bengkui, R.drawable.lxh_haojiong,
			R.drawable.lxh_zhenjing, R.drawable.lxh_biefanwo,
			R.drawable.lxh_buhaoyisi, R.drawable.lxh_xiudada,
			R.drawable.lxh_deyidexiao, R.drawable.lxh_jiujie,
			R.drawable.lxh_geijin, R.drawable.lxh_beicui,
			R.drawable.lxh_shuaishuaishou, R.drawable.lxh_haobang,
			R.drawable.lxh_qiaoqiao, R.drawable.lxh_buxiangshangban,
			R.drawable.lxh_kunsile, R.drawable.lxh_xuyuan,
			R.drawable.lxh_qiubite, R.drawable.lxh_youyali,
			R.drawable.lxh_xiangyixiang, R.drawable.lxh_zaokuangzheng,
			R.drawable.lxh_zhuanfa, R.drawable.lxh_xianghumobai,
			R.drawable.lxh_leifeng, R.drawable.lxh_jiekexun,
			R.drawable.lxh_meigui, R.drawable.lxh_holdzhu,
			R.drawable.lxh_quntiweiguan, R.drawable.lxh_tuijian,
			R.drawable.lxh_zana, R.drawable.lxh_beidian, R.drawable.lxh_pili };

	public final static String[] FACE_LTEXTS = new String[] { "[笑哈哈]", "[好爱哦]",
			"[噢耶]", "[偷乐]", "[泪流满面]", "[巨汗]", "[抠鼻屎]", "[求关注]", "[好喜欢]",
			"[崩溃]", "[好囧]", "[震惊]", "[别烦我]", "[不好意思]", "[羞嗒嗒]", "[得意地笑]",
			"[纠结]", "[给劲]", "[悲催]", "[甩甩手]", "[好棒]", "[瞧瞧]", "[不想上班]", "[困死了]",
			"[许愿]", "[丘比特]", "[有鸭梨]", "[想一想]", "[躁狂症]", "[转发]", "[互相膜拜]",
			"[雷锋]", "[杰克逊]", "[玫瑰]", "[hold住]", "[群体围观]", "[推荐]", "[赞啊]",
			"[被电]", "[霹雳]" };

	/**
	 * 表情字符替换成res标签形式。
	 * 
	 * @param str
	 * @return
	 */
	public final static String faceToHtml(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}
		for (int i = 0; i < FACE_DTEXTS.length; i++) {
			String face = FACE_DTEXTS[i];
			if (str.contains(face)) {
				String faceStr = "<img src='" + FACE_RES_DIDS[i]
						+ "' vertical-align='middle'/>";
				str = str.replace(face, faceStr);
			}
		}
		for (int i = 0; i < FACE_LTEXTS.length; i++) {
			String face = FACE_LTEXTS[i];
			if (str.contains(face)) {
				String faceStr = "<img src='" + FACE_RES_LIDS[i]
						+ "' vertical-align='middle'/>";
				str = str.replace(face, faceStr);
			}
		}
		return str;
	}

}
