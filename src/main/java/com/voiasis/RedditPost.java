package com.voiasis;

import java.util.List;

class LinkFlairRichtext {
    public String e;
    public String t;
}

class MediaEmbed {
}

class SecureMediaEmbed {
}

class AuthorFlairRichtext {
    public String e;
    public String t;
}

class Gildings {
    public int gid_1;
    public int gid_2;
}

class Source {
    public String url;
    public int width;
    public int height;
}

class Resolution {
    public String url;
    public int width;
    public int height;
}

class Variants {
}

class Image {
    public Source source;
    public List<Resolution> resolutions;
    public Variants variants;
    public String id;
}

class Preview {
    public List<Image> images;
    public boolean enabled;
}

class ResizedIcon {
    public String url;
    public int width;
    public int height;
}

class AllAwarding {
    public int giver_coin_reward;
    public String subreddit_id;
    public boolean is_new;
    public int days_of_drip_extension;
    public int coin_price;
    public String id;
    public int penny_donate;
    public int coin_reward;
    public String icon_url;
    public int days_of_premium;
    public int icon_height;
    public List<ResizedIcon> resized_icons;
    public int icon_width;
    public Object start_date;
    public boolean is_enabled;
    public String description;
    public Object end_date;
    public int subreddit_coin_reward;
    public int count;
    public String name;
    public String icon_format;
    public String award_sub_type;
    public int penny_price;
    public String award_type;
}

class Data {
    public Object approved_at_utc;
    public String subreddit;
    public String selftext;
    public String author_fullname;
    public boolean saved;
    public Object mod_reason_title;
    public int gilded;
    public boolean clicked;
    public String title;
    public List<LinkFlairRichtext> link_flair_richtext;
    public String subreddit_name_prefixed;
    public boolean hidden;
    public int pwls;
    public String link_flair_css_class;
    public int downs;
    public int thumbnail_height;
    public boolean hide_score;
    public String name;
    public boolean quarantine;
    public String link_flair_text_color;
    public String author_flair_background_color;
    public String subreddit_type;
    public int ups;
    public int total_awards_received;
    public MediaEmbed media_embed;
    public int thumbnail_width;
    public String author_flair_template_id;
    public boolean is_original_content;
    public List<Object> user_reports;
    public Object secure_media;
    public boolean is_reddit_media_domain;
    public boolean is_meta;
    public Object category;
    public SecureMediaEmbed secure_media_embed;
    public String link_flair_text;
    public boolean can_mod_post;
    public int score;
    public Object approved_by;
    public boolean author_premium;
    public String thumbnail;
    public Object author_flair_css_class;
    public List<AuthorFlairRichtext> author_flair_richtext;
    public Gildings gildings;
    public String post_hint;
    public Object content_categories;
    public boolean is_self;
    public Object mod_note;
    public double created;
    public String link_flair_type;
    public int wls;
    public Object removed_by_category;
    public Object banned_by;
    public String author_flair_type;
    public String domain;
    public boolean allow_live_comments;
    public Object selftext_html;
    public Object likes;
    public Object suggested_sort;
    public Object banned_at_utc;
    public Object view_count;
    public boolean archived;
    public boolean no_follow;
    public boolean is_crosspostable;
    public boolean pinned;
    public boolean over_18;
    public Preview preview;
    public List<AllAwarding> all_awardings;
    public List<Object> awarders;
    public boolean media_only;
    public String link_flair_template_id;
    public boolean can_gild;
    public boolean spoiler;
    public boolean locked;
    public String author_flair_text;
    public boolean visited;
    public Object removed_by;
    public Object num_reports;
    public Object distinguished;
    public String subreddit_id;
    public Object mod_reason_by;
    public Object removal_reason;
    public String link_flair_background_color;
    public String id;
    public boolean is_robot_indexable;
    public Object report_reasons;
    public String author;
    public Object discussion_type;
    public int num_comments;
    public boolean send_replies;
    public String whitelist_status;
    public boolean contest_mode;
    public List<Object> mod_reports;
    public boolean author_patreon_flair;
    public String author_flair_text_color;
    public String permalink;
    public String parent_whitelist_status;
    public boolean stickied;
    public String url;
    public int subreddit_subscribers;
    public double created_utc;
    public int num_crossposts;
    public Object media;
    public boolean is_video;
}

class Child {
    public String kind;
    public Data data;
}

class Dataa {
    public String modhash;
    public int dist;
    public List<Child> children;
    public String after;
    public String before;
}

public class RedditPost {
    public String kind;
    public Dataa data;
}
