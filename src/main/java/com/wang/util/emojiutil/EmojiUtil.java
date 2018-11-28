package com.wang.util.emojiutil;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;

import java.util.List;

/**
 * Created by wangyanwei on 2018/7/18.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class EmojiUtil extends EmojiParser{

    /**
     * 获取非表情字符串
     * @param input
     * @return
    */
    public static String getNonEmojiString(String input) {
        int prev = 0;
        StringBuilder sb = new StringBuilder();
        List<UnicodeCandidate> replacements = getUnicodeCandidates(input);
        for (UnicodeCandidate candidate : replacements) {
            sb.append(input.substring(prev, candidate.getEmojiStartIndex()));
            prev = candidate.getFitzpatrickEndIndex();
        }
        return sb.append(input.substring(prev)).toString();
    }

    /**
     * 获取表情字符串
     * @param input
     * @return
     */
    public static String getEmojiUnicodeString(String input){
        EmojiTransformer  transformer = new EmojiTransformer() {
            @Override
            public String transform(UnicodeCandidate unicodeCandidate) {
                return unicodeCandidate.getEmoji().getHtmlHexadecimal();
            }
        };
        StringBuilder sb = new StringBuilder();
        List<UnicodeCandidate> replacements = getUnicodeCandidates(input);
        for (UnicodeCandidate candidate : replacements) {
            sb.append(transformer.transform(candidate));
        }
        return  parseToUnicode(sb.toString());
    }

    public static String getUnicode(String source){
        String returnUniCode=null;
        String uniCodeTemp=null;
        for(int i=0;i<source.length();i++){
            uniCodeTemp = "\\u"+Integer.toHexString((int)source.charAt(i));
            returnUniCode=returnUniCode==null?uniCodeTemp:returnUniCode+uniCodeTemp;
        }
        return returnUniCode;
    }

    public static void main(String[] args) {
        /*EmojiParser.parseToAliases(string); 将表情符号转为字符
        EmojiParser.parseToUnicode(string); 将字符转为表情符号*/
        String str = "表情符号，☺☺☺";
        //转换表情
        System.out.println(str.length());
        System.out.println(getNonEmojiString(str));
        System.out.println(EmojiParser.parseToAliases(str));
        String emojiString = EmojiParser.parseToAliases(str)+":relaxed:";
        System.out.println(EmojiParser.parseToUnicode(emojiString));
    }
}
