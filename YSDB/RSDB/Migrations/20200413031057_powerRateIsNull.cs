using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class powerRateIsNull : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<decimal>(
                name: "powerRate",
                table: "p_rdEquipment",
                nullable: true,
                oldClrType: typeof(decimal));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<decimal>(
                name: "powerRate",
                table: "p_rdEquipment",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);
        }
    }
}
