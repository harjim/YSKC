using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations;
using EXPERTDB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace EXPERTDB
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
        }

        public DbSet<e_user> e_user { get; set; }
        public DbSet<app_menu> app_menu { get; set; }
        public DbSet<app_user_role> app_user_role { get; set; }
        public DbSet<app_role> app_role { get; set; }
        public DbSet<app_role_menu> app_role_menu { get; set; }
        public DbSet<e_award> e_award { get; set; }
        public DbSet<e_bank_info> e_bank_info { get; set; }
        public DbSet<e_book> e_book { get; set; }
        public DbSet<e_intellectual_property> e_intellectual_property { get; set; }
        public DbSet<e_paper> e_paper { get; set; }
        public DbSet<e_user_info> e_user_info { get; set; }
        public DbSet<e_extra> e_extra { get; set; }
        public DbSet<sys_log> sys_log { get; set; }
    }
}
