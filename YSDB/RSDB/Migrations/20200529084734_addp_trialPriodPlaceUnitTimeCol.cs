using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addp_trialPriodPlaceUnitTimeCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "workshopId",
                table: "p_trialProd");

            migrationBuilder.AddColumn<TimeSpan>(
                name: "endTime",
                table: "p_trialProd",
                nullable: false,
                defaultValue: new TimeSpan(0, 0, 0, 0, 0));

            migrationBuilder.AddColumn<string>(
                name: "place",
                table: "p_trialProd",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "startTime",
                table: "p_trialProd",
                nullable: false,
                defaultValue: new TimeSpan(0, 0, 0, 0, 0));

            migrationBuilder.AddColumn<string>(
                name: "unit",
                table: "p_trialProd",
                maxLength: 10,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "endTime",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "place",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "startTime",
                table: "p_trialProd");

            migrationBuilder.DropColumn(
                name: "unit",
                table: "p_trialProd");

            migrationBuilder.AddColumn<int>(
                name: "workshopId",
                table: "p_trialProd",
                nullable: true);
        }
    }
}
