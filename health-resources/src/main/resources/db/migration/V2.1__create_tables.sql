-- use a SERIAL or BIGSERIAL data type, which creates a sequence behind the scenes and increments/uses it at insert time
-- and if you want to reference it from another table, use integer or bigint

--
-- HealthX
-- Name: HEALTH_PROFILE; Type: TABLE; Schema: healthx; Owner: -
--
create table health_profile (
    id bigserial not null,
    username varchar(255) not null,
    primary key (id)
);

--
-- HealthX
-- Name: HEALTH_METRIC; Type: TABLE; Schema: healthx; Owner: -
--
create table health_metric (
    id bigserial not null,
    value double precision not null,
    type varchar(255) not null,
    profile_id bigint not null,
    primary key (id)
);

alter table health_metric drop constraint if exists FKhealthmetricprofile;
alter table health_metric add constraint FKhealthmetricprofile foreign key (profile_id) references health_profile (id);
