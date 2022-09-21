using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterPatent_masterExtendTablebase : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "createTime",
                table: "patent_master",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "creatorId",
                table: "patent_master",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "patent_master",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "patent_master",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "createTime",
                table: "patent_master");

            migrationBuilder.DropColumn(
                name: "creatorId",
                table: "patent_master");

            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "patent_master");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "patent_master");
        }
    }
}
