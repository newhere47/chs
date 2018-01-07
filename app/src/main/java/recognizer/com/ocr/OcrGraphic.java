package recognizer.com.ocr;




import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;

import recognizer.com.ocr.ui.camera.GraphicOverlay;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private int mId;

    private static final int WHITE = Color.WHITE;
    private static final int RED = Color.RED;
    private static final int BLUE = Color.BLUE;
    private static final int GREEN = Color.GREEN;

    private static Paint sRectPaint;
    private static Paint sTextPaint;
    private final TextBlock mText;

    OcrGraphic(GraphicOverlay overlay, TextBlock text) {
        super(overlay);

        mText = text;

        if (sRectPaint == null) {
            sRectPaint = new Paint();
            sRectPaint.setColor(RED);
            sRectPaint.setStyle(Paint.Style.STROKE);
            sRectPaint.setStrokeWidth(3.0f);
        }

        if (sTextPaint == null) {
            sTextPaint = new Paint();
            sTextPaint.setColor(GREEN);
            sTextPaint.setTextSize(1.0f);
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public TextBlock getTextBlock() {
        return mText;
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    public boolean contains(float x, float y) {
        TextBlock text = mText;
        if (text == null) {
            return false;
        }
        RectF rect = new RectF(text.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        return (rect.left < x && rect.right > x && rect.top < y && rect.bottom > y);
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        TextBlock text = mText;
        List<? extends Text> lineBoxes = text.getComponents();
        List<? extends Text> wordBoxes;
        if (text == null) {
            return;
        }
        RectF rect = new RectF(text.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
      // RectPaint.set
        canvas.drawRect(rect, sRectPaint);
        // Draws the bounding box around the TextBlock.
        for(Text line : lineBoxes) {
            wordBoxes = line.getComponents();
            for(Text word : wordBoxes) {
                float left = translateX(word.getBoundingBox().left);
                float bottom = translateY(word.getBoundingBox().bottom);
                sTextPaint.setTextSize(getCorrectSize(word.getValue(),word.getBoundingBox().width()));
                canvas.drawText(word.getValue(), left, bottom, sTextPaint);
            }
        }


        // Break the text into multiple lines and draw each one according to its own bounding box.
      /*  List<? extends Text> boxComponents = text.getComponents();
        List<? extends Text> lineComponents;a
        List<? extends Text> words;
        for(Text currentText : boxComponents) {
            lineComponents  = currentText.getComponents();
            for(Text currentComponent : lineComponents) {
                float left = translateX(currentComponent.getBoundingBox().left);
                float bottom = translateY(currentComponent.getBoundingBox().bottom);
                sTextPaint.setTextSize(currentComponent.getBoundingBox().width());
                canvas.drawText(currentComponent.getValue(), left, bottom, sTextPaint);
            }
        }*/
    }

    private int getCorrectSize(String text, int boxWidth){
//        float [] widths = new float[text.length()];
//        int currenctWidth = sTextPaint.getTextWidths(text,widths);
        int numberOfLettters = text.length();
        return (boxWidth/numberOfLettters);
    }
}
