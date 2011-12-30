package org.technbolts.jbehave.parser;

import org.technbolts.jbehave.support.JBKeyword;
import org.technbolts.util.CharTree;

public class StoryPart {
    private final int offset;
    private final String content;
    private JBKeyword preferredKeyword;
    
    public StoryPart(int offset, String content) {
        super();
        this.offset = offset;
        this.content = content;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public int getLength() {
        return content.length();
    }
    
    public String getContent() {
        return content;
    }
    
    /**
     * @return
     * @see #extractKeyword(CharTree)
     */
    public JBKeyword getPreferredKeyword() {
        return getPreferredKeyword(defaultTree());
    }
    
    public void setPreferredKeyword(JBKeyword keyword) {
        this.preferredKeyword = keyword;
    }
    
    public JBKeyword getPreferredKeyword(CharTree<JBKeyword> kwTree) {
        if(preferredKeyword==null)
            preferredKeyword = extractKeyword(kwTree);
        return preferredKeyword;
    }
    
    public JBKeyword extractKeyword() {
        return extractKeyword(defaultTree());
    }
    
    public JBKeyword extractKeyword(CharTree<JBKeyword> kwTree) {
        return kwTree.lookup(getContent());
    }
    
    public boolean startsWithKeyword () {
        return startsWithKeyword(defaultTree());
    }

    public boolean startsWithKeyword (CharTree<JBKeyword> kwTree) {
        return (getPreferredKeyword(kwTree)!=null);
    }
    
    private CharTree<JBKeyword> defaultTree() {
        return Constants.sharedKeywordCharTree();
    }

    /**
     * Same as {@link #getOffset()}
     * @see #getOffset()
     */
    public int getOffsetStart() {
        return getOffset();
    }
    
    public int getOffsetEnd() {
        return getOffset()+getLength();
    }

    public boolean intersects(int offset, int length) {
        int tmin = getOffset();
        int tmax = getOffsetEnd();
        int omin = offset;
        int omax = offset+length;
        return omin<=tmax && tmin<=omax;
    }

    public boolean isStepPart() {
        JBKeyword keyword = getPreferredKeyword();
        return keyword!=null && keyword.isStep();
    }

}
