package project.iot_software.mykerberos;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Date;

public class TodayDecorator implements DayViewDecorator {
    private CalendarDay today;

    public TodayDecorator() {
        today = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return today != null && day.equals(today);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD_ITALIC));
        view.addSpan(new ForegroundColorSpan(Color.DKGRAY));
        view.addSpan(new RelativeSizeSpan(1.6f));

    }

    public void setToday(Date date) {
        this.today = CalendarDay.from(date);
    }
}
