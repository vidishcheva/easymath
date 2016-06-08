package a.vidishcheva.easymath.experimental_lab;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * Created by Алёна on 11.04.2016.
 */
public class InputFilterForOnePowerEquation implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
        Spanned dest, int dstart, int dend) {

        if (source instanceof SpannableStringBuilder) {
        SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder)source;
        for (int i = end - 1; i >= start; i--) {
            char currentChar = source.charAt(i);
                if (currentChar == '+' && dest.charAt(dest.length()-2) == '*') {
                    sourceAsSpannableBuilder.delete(i, i+1);
                } else {
                }
            }
            return source;
        } else {
            StringBuilder filteredStringBuilder = new StringBuilder();
            for (int i = start; i < end; i++) {
                char currentChar = source.charAt(i);
                if (currentChar == '+' && dest.charAt(dest.length() - 2) == '*') {
                } else {
                    filteredStringBuilder.append(currentChar);
                }
            }
            System.out.println(filteredStringBuilder);
            return filteredStringBuilder.toString();
        }
    }
}
