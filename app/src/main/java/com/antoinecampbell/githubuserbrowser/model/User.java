package com.antoinecampbell.githubuserbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Parcelable {

    private String login;
    private int id;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("public_repos")
    private int publicRepos;
    @JsonProperty("public_gists")
    private int publicGists;
    private int followers;
    private int following;

    private String url;
    @JsonProperty("followers_url")
    private String followersUrl;
    @JsonProperty("following_url")
    private String followingUrl;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }

    public int getPublicGists() {
        return publicGists;
    }

    public void setPublicGists(int publicGists) {
        this.publicGists = publicGists;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeInt(this.id);
        dest.writeString(this.avatarUrl);
        dest.writeInt(this.publicRepos);
        dest.writeInt(this.publicGists);
        dest.writeInt(this.followers);
        dest.writeInt(this.following);
        dest.writeString(this.url);
        dest.writeString(this.followersUrl);
        dest.writeString(this.followingUrl);
    }

    private User(Parcel in) {
        this.login = in.readString();
        this.id = in.readInt();
        this.avatarUrl = in.readString();
        this.publicRepos = in.readInt();
        this.publicGists = in.readInt();
        this.followers = in.readInt();
        this.following = in.readInt();
        this.url = in.readString();
        this.followersUrl = in.readString();
        this.followingUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {

        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (followers != user.followers) {
            return false;
        }
        if (following != user.following) {
            return false;
        }
        if (id != user.id) {
            return false;
        }
        if (publicGists != user.publicGists) {
            return false;
        }
        if (publicRepos != user.publicRepos) {
            return false;
        }
        if (avatarUrl != null ? !avatarUrl.equals(user.avatarUrl) : user.avatarUrl != null) {
            return false;
        }
        if (followersUrl != null ? !followersUrl.equals(user.followersUrl) :
                user.followersUrl != null) {
            return false;
        }
        if (followingUrl != null ? !followingUrl.equals(user.followingUrl) :
                user.followingUrl != null) {
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (url != null ? !url.equals(user.url) : user.url != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + publicRepos;
        result = 31 * result + publicGists;
        result = 31 * result + followers;
        result = 31 * result + following;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (followersUrl != null ? followersUrl.hashCode() : 0);
        result = 31 * result + (followingUrl != null ? followingUrl.hashCode() : 0);
        return result;
    }

}
