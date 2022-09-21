using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using ESDB.Models;

namespace ESDB
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<talent_delivery>().HasIndex(a => new { a.userId, a.talentId }).IsUnique(true);

            modelBuilder.Entity<user>().Property(a => a.disabled).HasDefaultValue(0);
            modelBuilder.Entity<config_data>().Property(a => a.disabled).HasDefaultValue(0);
            modelBuilder.Entity<config_module>().Property(a => a.disabled).HasDefaultValue(0);
            base.OnModelCreating(modelBuilder);
        }
        public DbSet<sys_log> sys_log { get; set; }
        public DbSet<config_data> config_data { get; set; }
        public DbSet<config_module> config_module { get; set; }
        public DbSet<sys_dictionary> sys_dictionary { get; set; }
        public DbSet<user_expert> user_expert { get; set; }
        public DbSet<user_files> user_files { get; set; }
        public DbSet<user_info> user_info { get; set; }
        public DbSet<user> user { get; set; }
        public DbSet<article> article { get; set; }
        public DbSet<user_expert_log> user_expert_log { get; set; }
        public DbSet<app_menu> app_menu { get; set; }
        public DbSet<achievement> achievement { get; set; }
        public DbSet<achievement_files> achievement_files { get; set; }
        public DbSet<achievement_log> achievement_log { get; set; }
        public DbSet<e_cooperation> e_cooperation { get; set; }
        public DbSet<tech_requirement> tech_requirement { get; set; }
        public DbSet<talent_requirement> talent_requirement { get; set; }
        public DbSet<talent_delivery> talent_delivery { get; set; }
    }
}
