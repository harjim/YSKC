using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addSolutiuonRateUserIdAndTimeCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "rateTime",
                table: "solution",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "rateUserId",
                table: "solution",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rateTime",
                table: "solution");

            migrationBuilder.DropColumn(
                name: "rateUserId",
                table: "solution");
        }
    }
}
