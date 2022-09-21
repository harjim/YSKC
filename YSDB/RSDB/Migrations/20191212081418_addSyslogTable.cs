using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addSyslogTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "sys_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    description = table.Column<string>(maxLength: 100, nullable: true),
                    logType = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    username = table.Column<string>(maxLength: 100, nullable: false),
                    source = table.Column<int>(nullable: false),
                    logTime = table.Column<DateTime>(nullable: false),
                    logUrl = table.Column<string>(maxLength: 100, nullable: false),
                    logParams = table.Column<string>(maxLength: 1000, nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_sys_log", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "sys_log");
        }
    }
}
