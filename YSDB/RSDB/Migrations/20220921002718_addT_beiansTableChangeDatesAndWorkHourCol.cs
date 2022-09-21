using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addT_beiansTableChangeDatesAndWorkHourCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "workHour",
                table: "t_investment",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "workHour",
                table: "t_equipment",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<string>(
                name: "changedDates",
                table: "t_beian",
                maxLength: 100,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "workHour",
                table: "t_investment");

            migrationBuilder.DropColumn(
                name: "workHour",
                table: "t_equipment");

            migrationBuilder.DropColumn(
                name: "changedDates",
                table: "t_beian");
        }
    }
}
