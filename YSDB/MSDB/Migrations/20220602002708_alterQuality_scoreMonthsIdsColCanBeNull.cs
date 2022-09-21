using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterQuality_scoreMonthsIdsColCanBeNull : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "monthsId",
                table: "quality_score",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "monthsId",
                table: "quality_score",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
