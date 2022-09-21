using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class p_attendance_used : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "usedId",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "p_attendance_used",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    enumber = table.Column<string>(maxLength: 30, nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    usedAttData = table.Column<string>(maxLength: 120, nullable: false),
                    workHours = table.Column<decimal>(nullable: false),
                    remainHours = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_attendance_used", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_attendance_used");

            migrationBuilder.DropColumn(
                name: "usedId",
                table: "p_attendance");
        }
    }
}
