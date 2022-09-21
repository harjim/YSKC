using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_rdAgg_configExtendTablebase : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "lastUpdateTime",
                table: "p_rdAgg_config",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "lastUpdatorId",
                table: "p_rdAgg_config",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "msLastUpdatorId",
                table: "p_rdAgg_config",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "lastUpdateTime",
                table: "p_rdAgg_config");

            migrationBuilder.DropColumn(
                name: "lastUpdatorId",
                table: "p_rdAgg_config");

            migrationBuilder.DropColumn(
                name: "msLastUpdatorId",
                table: "p_rdAgg_config");
        }
    }
}
